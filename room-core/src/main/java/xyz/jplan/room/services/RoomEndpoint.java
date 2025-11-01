package xyz.jplan.room.services;

import java.io.IOException;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import xyz.jplan.room.services.data.Message;

@ServerEndpoint(value = "/room/{roomId}/planning", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RoomEndpoint {

    private static final String ROOM_ID = "ROOM_ID";
    private static final String USER_NAME = "USER_NAME";

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
	try {
	    session.getBasicRemote().sendObject(new Message(roomId, "Hello from Room endpoint"));
	    session.getUserProperties().put(ROOM_ID, roomId);
	} catch (IOException | EncodeException e) {
	    e.printStackTrace();
	}
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
	session.getUserProperties().putIfAbsent(USER_NAME, message.getFrom());
	broadcast(session, message);
    }

    @OnClose
    public void onClose(Session session) {
	String roomId = session.getPathParameters().get("roomId");
	broadcast(session,
		new Message(roomId, session.getUserProperties().getOrDefault(USER_NAME, "") + ", disconnected!"));
    }

    private void broadcast(Session session, Message message) {
	session.getOpenSessions().forEach(ses -> {
	    try {
		if (ses.isOpen()) {
		    ses.getBasicRemote().sendObject(message);
		}
	    } catch (IOException | EncodeException e) {
		e.printStackTrace();
	    }
	});
    }
}
