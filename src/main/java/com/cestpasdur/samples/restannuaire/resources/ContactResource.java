package com.cestpasdur.samples.restannuaire.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.cestpasdur.samples.restannuaire.domain.Contact;
import javax.ws.rs.Produces;


@Path("contact")
public class ContactResource {


    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public Contact getContactXML(@PathParam("id") int id) {
        Contact contact = new Contact();
        contact.setFirstName("Damien");
        contact.setLastName("Gouyette");
        contact.setMail("damien.gouyette at gmail dot com");
        return contact;
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

}
