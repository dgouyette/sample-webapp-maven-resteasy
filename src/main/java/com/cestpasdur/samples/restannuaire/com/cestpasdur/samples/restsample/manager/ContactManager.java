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
        Contact contact1 = new Contact("Damien", "Gouyette", "damien.gouyette@gmail.com");
        contacts.add(contact1);
        Contact contact2 = new Contact("Dupond", "Marcel", "mdupond@gmail.com");
        contacts.add(contact2);
        System.out.println("Contacts : "+contacts);
    }

    public synchronized Contact getContact(int id) {
        return contacts.get(id);
    }

    public synchronized int addContact(Contact contact) {
        contacts.add(contact);
        return contacts.indexOf(contact);
    }

    public synchronized void remove(final int id) {
        contacts.remove(id);
    }

    public synchronized void update(final int id, final Contact contact) {
        Contact contactToUpdate = contacts.get(id);
        contactToUpdate.setFirstName(contact.getFirstName());
        contactToUpdate.setLastName(contact.getLastName());
        contactToUpdate.setMail(contact.getMail());
    }

    public synchronized List<Contact> getContacts()
    {
        return this.contacts;
    }

}
