package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.com.cestpasdur.samples.restsample.manager.ContactManager;
import com.cestpasdur.samples.restannuaire.domain.Contact;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Path("contact")
public class ContactResource {


    /**
     * Partie simulant la persistances **
     */

    private Map<Integer, Contact> contactDB = new ConcurrentHashMap<Integer, Contact>();
    private AtomicInteger idCounter = new AtomicInteger();

    public Map<Integer, Contact> getContactDB() {
        return contactDB;
    }

    public void setContactDB(Map<Integer, Contact> contactDB) {
        this.contactDB = contactDB;
    }

    /**
     * Fin persistance **
     */


    @GET
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Contact getContactXML(@PathParam("id") int id) {
        Contact retour = new Contact();

        retour = contactDB.get(id);
        if (retour == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return retour;
    }


    @POST
    @Consumes("application/xml")
    public Response AddContact(final Contact contact) throws URISyntaxException {
        contactDB.put(idCounter.getAndIncrement(), contact);
        return Response.status(HttpResponseCodes.SC_CREATED).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes({"application/xml", "text/xml", "application/json"})
    public Response updateContact(@PathParam("id") final int id, final Contact contact) {
        try {
            Contact contactToUpdate = contactDB.get(id);
            contactToUpdate.setFirstName(contact.getFirstName());
            contactToUpdate.setLastName(contact.getLastName());
            contactToUpdate.setMail(contact.getMail());
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
            contactDB.remove(id);
        }
        catch (IndexOutOfBoundsException e) {
            //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.status(HttpResponseCodes.SC_NO_CONTENT).build();
    }


}
