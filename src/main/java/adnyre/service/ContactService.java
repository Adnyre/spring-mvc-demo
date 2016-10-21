package adnyre.service;

import adnyre.model.Contact;

import java.util.List;

public interface ContactService {
    Contact updateContact(Contact contact) throws ServiceException;

    Contact createContact(Contact contact) throws ServiceException;

    void deleteContact(Contact contact) throws ServiceException;

    ContactDto getContactById(int id) throws ServiceException;

    List<ContactDto> getAllContacts() throws ServiceException;
}
