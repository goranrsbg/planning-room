package xyz.jplan.room.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/room/{roomId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RoomEndpoint {

    private static Logger logger = LoggerFactory.getLogger(RoomEndpoint.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
	try {
	    logger.info("New session id={0} room={1}", session.getId(), roomId);
	    session.getBasicRemote().sendObject(new Message(roomId, "Hello from Room endpoint"));
	} catch (IOException | EncodeException e) {
	    e.printStackTrace();
	}
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
	logger.info("Recived session id={0} message={1}", session.getId(), message.toString());
	broadcast(session, message);
    }

    @OnClose
    public void onClose(Session session) {
	logger.info("Session to close id={0}", session.getId());
	String roomId = session.getPathParameters().get("roomId");
	broadcast(session, new Message(roomId, "Disconnected!"));
    }

    private void broadcast(Session session, Message message) {
	session.getOpenSessions().forEach(ses -> {
	    try {
		ses.getBasicRemote().sendObject(message);
	    } catch (IOException | EncodeException e) {
		e.printStackTrace();
	    }
	});
    }
}
