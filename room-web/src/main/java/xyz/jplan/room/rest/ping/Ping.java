package xyz.jplan.room.rest.ping;

public class Ping {

    final String message;

    public Ping(String suffix) {
        String end = suffix == null ? ""
                                    : " " + suffix;
        message = System.currentTimeMillis() + end;
    }

}