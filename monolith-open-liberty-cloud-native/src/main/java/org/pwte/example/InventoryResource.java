package org.pwte.example;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.openliberty.guides.models.SystemLoad;

@ApplicationScoped
@Path("/jaxrs")
public class InventoryResource {

    private static Logger logger = Logger.getLogger(InventoryResource.class.getName());

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
