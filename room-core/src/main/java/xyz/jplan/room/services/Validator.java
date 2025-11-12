package xyz.jplan.room.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.websocket.Session;

@Stateless
public class Validator {

    Logger log = LoggerFactory.getLogger(Validator.class);

    private double[] validCardValues = { 0d, 0.5d, 1d, 1.5d, 2d, 2.5d, 3d, 3.5d, 4d, 4.5d, 5d, 8d, 13d, 21d, 34d, 55d };

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

    public boolean isCardValueValid(String cardValue) {
	double cardNumber = -1d;
	try {
	    cardNumber = Double.parseDouble(cardValue);
	} catch (NumberFormatException | NullPointerException e) {
	    log.error("Not valid card value: {0}", cardValue, e);
	    return false;
	}
	for (double cv : validCardValues) {
	    if (cv == cardNumber) {
		return true;
	    }
	}
	return false;
    }
}
