package xyz.jplan.room.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import xyz.jplan.room.services.data.Message;

@ServerEndpoint(value = "/planning", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RoomEndpoint {

    Logger log = LoggerFactory.getLogger(RoomEndpoint.class);

    private Jsonb jsonb = JsonbBuilder.create();
    private static final String USER_KEY = "USER_NAME";
    private static final String ROOM_KEY = "ROOM_ID";

    @Inject
    private Validator validator;

    @OnOpen
    public void onOpen(Session session) {
	try {
	    session.getBasicRemote().sendObject(new Message("SERVER", "Hello from server."));
	} catch (IOException | EncodeException e) {
	    log.error("Error on sending message {0}", "Hello from server.", e);
	}
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
	try {
	    switch (message.getAction()) {
	    case "SET_NAME" -> {
		String name = message.getData();
		if (validator.isNameValid(name)) {
		    session.getUserProperties().put(USER_KEY, name);
		    session.getBasicRemote().sendObject(new Message("NAME_IS_SET", name));
		} else {
		    session.getBasicRemote().sendObject(new Message("NAME_NOT_VALID", name));
		}
	    }
	    case "CREATE_ROOM" -> {
		String room = message.getData();
		if (validator.isRoomIdValid(room)
			&& validator.isRoomIdUnique(session.getOpenSessions(), room, ROOM_KEY)) {
		    session.getUserProperties().put(ROOM_KEY, room);
		    String players = gatherPlayers(session);
		    session.getBasicRemote().sendObject(new Message("ROOM_CREATED", players));
		} else {
		    session.getBasicRemote().sendObject(new Message("ROOM_ID_NOT_VALID", room));
		}
	    }
	    case "JOIN_ROOM" -> {
		String room = message.getData();
		if (validator.isRoomIdValid(room)
			&& !validator.isRoomIdUnique(session.getOpenSessions(), room, ROOM_KEY)) {
		    session.getUserProperties().put(ROOM_KEY, room);
		    String players = gatherPlayers(session);
		    broadcast(session, players, "ROOM_JOIN");
		} else {
		    session.getBasicRemote().sendObject(new Message("ROOM_ID_NOT_VALID", room));
		}
	    }
	    case "CHAT" -> {
		broadcast(session, getChatMessage(session, message.getData()), "CHAT");
	    }
	    default ->
		session.getBasicRemote().sendObject(new Message("ERROR", "Unknown action: " + message.getAction()));
	    }
	} catch (IOException | EncodeException e) {
	    log.error("Error processing message {0}", message, e);
	}
    }

    @OnClose
    public void onClose(Session session) {
	broadcast(session, getChatMessage(session, "disconnected!"), "CHAT");
    }

    private String getChatMessage(Session session, String text) {
	String name = (String) session.getUserProperties().getOrDefault(USER_KEY, "");
	return String.format("%s: %s", name, text);
    }

    private String gatherPlayers(Session session) {
	List<String> players = new ArrayList<>();
	String room = (String) session.getUserProperties().getOrDefault(ROOM_KEY, "No room.");
	session.getOpenSessions().forEach(ses -> {
	    if (ses.isOpen()) {
		String roomValue = (String) ses.getUserProperties().getOrDefault(ROOM_KEY, "");
		if (roomValue.equals(room)) {
		    String name = (String) ses.getUserProperties().getOrDefault(USER_KEY, "");
		    players.add(name);
		}
	    }
	});
	return jsonb.toJson(players);
    }

    private void broadcast(Session session, String text, String action) {
	String room = (String) session.getUserProperties().getOrDefault(ROOM_KEY, "No room.");
	session.getOpenSessions().forEach(ses -> {
	    try {
		if (ses.isOpen()) {
		    String roomValue = (String) ses.getUserProperties().getOrDefault(ROOM_KEY, "");
		    if (roomValue.equals(room)) {
			ses.getBasicRemote().sendObject(new Message().withAction(action).withData(text));
		    }
		}
	    } catch (IOException | EncodeException e) {
		e.printStackTrace();
	    }
	});
    }
}
