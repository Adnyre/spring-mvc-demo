package adnyre.dao;

import adnyre.model.Contact;

import java.util.List;

public interface ContactDAO {
    Contact createContact(Contact contact);

    Contact updateContact(Contact contact);

    boolean deleteContact(Contact contact);

    Contact getContactById(long id);

    List<Contact> getAllContacts();
}
