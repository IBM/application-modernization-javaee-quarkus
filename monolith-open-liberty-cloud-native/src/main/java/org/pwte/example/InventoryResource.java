package org.pwte.example;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/jaxrs")
public class InventoryResource {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystems() {
        System.out.println("nikl");
        return Response
                .status(Response.Status.OK)
                .build();
    }

}
