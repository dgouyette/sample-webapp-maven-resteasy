package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.domain.Contact;
import com.cestpasdur.samples.restannuaire.com.cestpasdur.samples.restsample.manager.ContactManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;

import org.jboss.resteasy.util.HttpResponseCodes;


@Path("contact")
public class ContactResource {


    @GET
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Contact getContactXML(@PathParam("id") int id) {
        Contact retour = new Contact();
        try {
            retour = ContactManager.get().getContact(id);
        }
        catch (IndexOutOfBoundsException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return retour;
    }


    @POST
    @Consumes("application/xml")
    public Response AddContact(final Contact contact) throws URISyntaxException {
        int id = ContactManager.get().addContact(new Contact());
        System.out.println("Contact n°" + id + " crée : " + contact);
        return Response.status(HttpResponseCodes.SC_CREATED).build();
    }


    @PUT
    @Consumes({"application/xml", "text/xml", "application/json"})
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id) {
       return Response.status(HttpResponseCodes.SC_NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        
        try {
           ContactManager.get().remove(id);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.status(HttpResponseCodes.SC_NO_CONTENT).build();
    }


}
