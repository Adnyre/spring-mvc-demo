package adnyre.service;

import adnyre.model.Contact;

import java.util.List;

public interface ContactService {
    Contact createOrUpdateContact(Contact contact);

    boolean deleteContact(Contact contact);

    Contact getContactById(long id);

    List<Contact> getAllContacts();
}
