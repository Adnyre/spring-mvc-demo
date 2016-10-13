package adnyre.service;

import adnyre.model.Contact;

import java.util.List;

public interface ContactService {
    Contact updateContact(Contact contact) throws ServiceException;

    Contact createContact(Contact contact) throws ServiceException;

    boolean deleteContact(Contact contact) throws ServiceException;

    Contact getContactById(long id) throws ServiceException;

    List<Contact> getAllContacts() throws ServiceException;
}
