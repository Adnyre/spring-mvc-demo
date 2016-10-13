package adnyre.service;

import adnyre.dao.ContactDao;
import adnyre.dao.DaoException;
import adnyre.model.Contact;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactDao dao;

    @Override
    public Contact createContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Creating new contact");
            return dao.createContact(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::createContact", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Contact updateContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Updating contact: " + contact);
            return dao.updateContact(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::updateContact", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Deleting contact: " + contact);
            return dao.deleteContact(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::deleteContact", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Contact getContactById(long id) throws ServiceException {
        try {
            LOGGER.debug("Getting contact by id: " + id);
            return dao.getContactById(id);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::getContactById", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Contact> getAllContacts() throws ServiceException {
        try {
            LOGGER.debug("Getting all contacts");
            return dao.getAllContacts();
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::getAllContacts", e);
            throw new ServiceException(e);
        }
    }
}
