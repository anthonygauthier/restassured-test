package io.github.anthonygauthier.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 * Base endpoint for the ConnectivityResource class
 */
@Path("/")
public class HealthResource {
    /**
     * A simple endpoint testing the connectivity to the API
     * @return returns the string "connected" if successful
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getConnected() {
        return new JSONObject().put("status", "healthy").toString();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String postConnected() {
        return new JSONObject().put("status", "post-healthy").toString();
    }
}