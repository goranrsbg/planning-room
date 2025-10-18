package xyz.jplan.room.services;

import jakarta.ejb.Stateless;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.Serial;
import java.io.Serializable;

@Stateless
public class TestService implements Serializable {

    @Serial
    private static final long serialVersionUID = 101L;

    public String hello() {
        return "steady, ready, go";
    }
    
    public String hw() {
    	Jsonb jsonb = JsonbBuilder.create();
    	return jsonb.toJson(new Data("hello world"));
    }
}
