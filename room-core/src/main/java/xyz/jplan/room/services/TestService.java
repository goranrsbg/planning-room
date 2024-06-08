package xyz.jplan.room.services;

import jakarta.ejb.Stateless;

import java.io.Serializable;

@Stateless
public final class TestService implements Serializable {

    private static final long serialVersionUID = 101L;

    public String hello() {
        return "steady, ready, go";
    }
}
