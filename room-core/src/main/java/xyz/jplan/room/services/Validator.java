package xyz.jplan.room.services;

import java.util.Set;

import jakarta.ejb.Stateless;
import jakarta.websocket.Session;

@Stateless
public class Validator {

    public boolean isNameValid(String name) {
	return name != null && !name.isBlank();
    }

    public boolean isRoomIdValid(String roomId) {
	if (roomId == null || roomId.length() != 6) {
	    return false;
	}
	for (char ch : roomId.toCharArray()) {
	    if (!Character.isDigit(ch)) {
		return false;
	    }
	}
	return true;
    }

    public boolean isRoomIdUnique(Set<Session> sessions, String roomId, String roomKey) {
	for (Session session : sessions) {
	    if (session.isOpen()) {
		String room = (String) session.getUserProperties().getOrDefault(roomKey, "");
		if (roomId.equals(room)) {
		    return false;
		}
	    }
	}
	return true;
    }
}
