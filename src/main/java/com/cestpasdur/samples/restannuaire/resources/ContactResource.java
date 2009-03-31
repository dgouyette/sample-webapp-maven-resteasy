package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.domain.Contact;
import com.cestpasdur.samples.restannuaire.com.cestpasdur.samples.restsample.manager.ContactManager;

import javax.ws.rs.*;


@Path("contact")
public class ContactResource {


    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public Contact getContactXML(@PathParam("id") int id) {
       return ContactManager.get().getContact(id);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Contact getContactJSON(@PathParam("id") int id) {
        Contact contact = new Contact();
        contact.setFirstName("Damien");
        contact.setLastName("Gouyette");
        contact.setMail("damien.gouyette at gmail dot com");
        return contact;
    }

    @PUT
    @Consumes({"application/xml", "text/xml", "application/json"})
    public void updateContact() {
        
    }


}
