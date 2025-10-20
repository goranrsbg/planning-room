package xyz.jplan.room.rest.ping;

public class Ping {

    final String message;

    public Ping(String suffix) {
	String end = suffix == null ? "" : " " + suffix.trim();
	message = System.currentTimeMillis() + end;
    }

}