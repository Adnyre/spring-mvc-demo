package adnyre.dao;

import adnyre.model.Contact;

import java.util.List;

public interface ContactDAO {
    Contact createContact(Contact contact) throws DAOException;

    Contact updateContact(Contact contact) throws DAOException;

    boolean deleteContact(Contact contact) throws DAOException;

    Contact getContactById(long id) throws DAOException;

    List<Contact> getAllContacts() throws DAOException;
}
