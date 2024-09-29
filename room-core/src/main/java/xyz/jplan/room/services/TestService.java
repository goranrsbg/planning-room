package xyz.jplan.room.services;

import jakarta.ejb.Stateless;

import java.io.Serial;
import java.io.Serializable;

@Stateless
public class TestService implements Serializable {

    @Serial
    private static final long serialVersionUID = 101L;

    public String hello() {
        return "steady, ready, go";
    }
}
