package xyz.jplan.room.api.ping;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ping {

    public static final Logger log = Logger.getLogger(Ping.class.getName());

    private final String name;
    private final String value;

    public Ping(String zone) {
        name = "Ping";
        ZoneId zoneId = parseZoneId(zone);
        value = Instant.now().atZone(zoneId).toString();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    private ZoneId parseZoneId(String zoneId) {
        try {
            return ZoneId.of(zoneId);
        } catch (DateTimeException e) {
            log.log(Level.SEVERE, "time zone not valid: " + zoneId);
            return ZoneId.of("UTC");
        }
    }
}