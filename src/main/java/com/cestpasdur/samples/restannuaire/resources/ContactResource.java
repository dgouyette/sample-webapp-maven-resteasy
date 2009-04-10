package com.cestpasdur.samples.restannuaire.resources;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.util.HttpResponseCodes;

import com.cestpasdur.samples.restannuaire.domain.Contact;
import com.cestpasdur.samples.restannuaire.domain.Contacts;


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

    @POST
    @Consumes("application/xml")
    public Response AjouteContact(final Contact contact) throws URISyntaxException {
        contactDB.put(idCounter.getAndIncrement(), contact);

        //on retourne une réponse indiquant que l'entité à été crée
        return Response.status(HttpResponseCodes.SC_CREATED).build();
    }

    

    @GET
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Contact recupereContac(@PathParam("id") int id) {
        Contact retour;
        retour = contactDB.get(id);
        if (retour == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return retour;
    }

    

    @GET
    @Path("/all/")
    @Produces({"application/xml", "application/json"})
    public Contacts recupereContacts()   {
        Contacts contacts=new Contacts();
        contacts.setContacts(contactDB.values());
        if (contacts == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return contacts;
    }



  

    @PUT
    @Path("/{id}")
    @Consumes({"application/xml", "text/xml", "application/json"})
    public Response updateContact(@PathParam("id") final int id, final Contact contact) {
        
            Contact contactToUpdate = contactDB.get(id);
            if (contactToUpdate==null)
            {
                   //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
                   throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            contactToUpdate.setFirstName(contact.getFirstName());
            contactToUpdate.setLastName(contact.getLastName());
            contactToUpdate.setMail(contact.getMail());
    
     return Response.status(HttpResponseCodes.SC_OK).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        Contact contactRemoved=    contactDB.remove(id);
        
        if (contactRemoved==null)
        {
        	 //Si le contact n'est pas trouve, on retourne le code "non trouve" ce qui est different de "pas de contenu"
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.status(HttpResponseCodes.SC_NO_CONTENT).build();
    }


}
