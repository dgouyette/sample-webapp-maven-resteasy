package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.com.cestpasdur.samples.restsample.manager.ContactManager;
import com.cestpasdur.samples.restannuaire.domain.Contact;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;


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
        ContactManager.get().addContact(contact);
        return Response.status(HttpResponseCodes.SC_CREATED).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes({"application/xml", "text/xml", "application/json"})
    public Response updateContact(@PathParam("id")final int id, final Contact contact) {
        try {
            ContactManager.get().update(id,contact);
        }
        catch (IndexOutOfBoundsException e) {
            //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.status(HttpResponseCodes.SC_OK).build();
    }



    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {

        try {
            ContactManager.get().remove(id);
        }
        catch (IndexOutOfBoundsException e) {
             //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.status(HttpResponseCodes.SC_NO_CONTENT).build();
    }


}
