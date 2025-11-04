package xyz.jplan.room.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
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

    private static final String USER_NAME = "USER_NAME";
    private static final String ROOM_ID = "ROOM_ID";

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
		    session.getUserProperties().put(USER_NAME, name);
		    session.getBasicRemote().sendObject(new Message("NAME_IS_SET", name));
		} else {
		    session.getBasicRemote().sendObject(new Message("NAME_NOT_VALID", name));
		}
	    }
	    case "CRETE_ROOM" -> {
		String room = message.getData();
		session.getUserProperties().put(ROOM_ID, room);
		session.getBasicRemote().sendObject(new Message("ROOM_CREATED", room));
	    }
	    case "JOIN_ROOM" -> {
		String room = message.getData();
		session.getUserProperties().put(ROOM_ID, room);
		session.getBasicRemote().sendObject(new Message("ROOM_JOIN", room));
	    }
	    case "CHAT" -> {
		broadcast(session, message.getData());
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
	broadcast(session, "disconnected!");
    }

    private void broadcast(Session session, String text) {
	// String room = (String) session.getUserProperties().getOrDefault(ROOM_ID, "No
	// room.");
	session.getOpenSessions().forEach(ses -> {
	    try {
		// ((String) ses.getUserProperties().getOrDefault(ROOM_ID, "")).equals(room)
		if (ses.isOpen()) {
		    String name = (String) ses.getUserProperties().get(USER_NAME);
		    ses.getBasicRemote().sendObject(new Message().withAction("CHAT").withData(name + ": " + text));
		}
	    } catch (IOException | EncodeException e) {
		e.printStackTrace();
	    }
	});
    }
}
