package xyz.jplan.room.services;

import jakarta.ejb.Stateless;

@Stateless
public class Validator {

    public boolean isNameValid(String name) {
	return name != null && !name.isBlank();
    }

}
