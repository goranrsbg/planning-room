package xyz.jplan.room.api.ping;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("ping")
public class PingResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Ping ping(@QueryParam("tz") String timeZone) {
        if (timeZone == null || timeZone.isBlank()) {
            timeZone = "UTC";
        }
        return new Ping(timeZone);
    }
}