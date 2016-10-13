package adnyre.dao;

import adnyre.model.Contact;

import java.util.List;

public interface ContactDao {
    Contact createContact(Contact contact) throws DaoException;

    Contact updateContact(Contact contact) throws DaoException;

    boolean deleteContact(Contact contact) throws DaoException;

    Contact getContactById(long id) throws DaoException;

    List<Contact> getAllContacts() throws DaoException;
}
