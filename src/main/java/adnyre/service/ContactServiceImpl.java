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
            return dao.create(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::create", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Contact updateContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Updating contact: " + contact);
            return dao.update(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::update", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Deleting contact: " + contact);
            dao.delete(contact);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::delete", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Contact getContactById(long id) throws ServiceException {
        try {
            LOGGER.debug("Getting contact by id: " + id);
            return dao.find(id);
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::find", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Contact> getAllContacts() throws ServiceException {
        try {
            LOGGER.debug("Getting all contacts");
            return dao.findAll();
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::findAll", e);
            throw new ServiceException(e);
        }
    }
}
