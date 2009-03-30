package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.domain.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.io.OutputStream;
import java.io.IOException;


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
