package com.cestpasdur.samples.restannuaire.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("hello")
public class HelloResource {

    @GET
    @Path("/{qui}")
    public Response echoService(@PathParam("qui") String message) {
        return Response.status(200).entity(message).build();
    }

    @GET
    @Path("/{qui}")
    @Produces("application/xml")
    public Response echoServiceXml(@PathParam("qui") String message) {
        return Response.status(200).entity(message).build();
    }


}
