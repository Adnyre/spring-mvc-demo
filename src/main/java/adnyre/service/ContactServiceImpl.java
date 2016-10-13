package adnyre.service;

import adnyre.dao.ContactDAO;
import adnyre.dao.DAOException;
import adnyre.model.Contact;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactDAO dao;

    @Override
    public Contact createOrUpdateContact(Contact contact) throws ServiceException {
        try {
            if (contact.getId() == 0) {
                LOGGER.debug("Creating new contact");
                return dao.createContact(contact);
            } else {
                LOGGER.debug("Updating contact: " + contact);
                return dao.updateContact(contact);
            }
        } catch (DAOException e) {
            LOGGER.error("DAOException in ContactServiceImpl::createOrUpdateContact", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteContact(Contact contact) throws ServiceException {
        try {
            LOGGER.debug("Deleting contact: " + contact);
            return dao.deleteContact(contact);
        } catch (DAOException e) {
            LOGGER.error("DAOException in ContactServiceImpl::deleteContact", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Contact getContactById(long id) throws ServiceException {
        try {
            LOGGER.debug("Getting contact by id: " + id);
            return dao.getContactById(id);
        } catch (DAOException e) {
            LOGGER.error("DAOException in ContactServiceImpl::getContactById", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Contact> getAllContacts() throws ServiceException {
        try {
            LOGGER.debug("Getting all contacts");
            return dao.getAllContacts();
        } catch (DAOException e) {
            LOGGER.error("DAOException in ContactServiceImpl::getAllContacts", e);
            throw new ServiceException(e);
        }
    }
}
