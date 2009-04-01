package com.cestpasdur.samples.restannuaire.com.cestpasdur.samples.restsample.manager;

import com.cestpasdur.samples.restannuaire.domain.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactManager {

    private static ContactManager singleton = new ContactManager();

    public static ContactManager get() {
        return singleton;
    }

    private final List<Contact> contacts = new ArrayList<Contact>();

    {
        Contact contact1 =new Contact("Damien", "Gouyette", "damien.gouyette@gmail.com");
        contacts.add(contact1);
        Contact contact2 =new Contact("Dupond", "Marcel", "mdupond@gmail.com");
        contacts.add(contact2);
        
    }

    public synchronized Contact getContact(int id){
        return contacts.get(id);
    }

    public synchronized int addContact(final Contact contact) {
        contacts.add(contact);
        return contacts.indexOf(contact);
    }

    public synchronized void remove(final int id){
        contacts.remove(id);
    }

}
