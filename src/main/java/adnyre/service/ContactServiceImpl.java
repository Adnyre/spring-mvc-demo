package adnyre.service;

import adnyre.dao.ContactDAO;
import adnyre.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO dao;

    @Override
    public boolean createOrUpdateContact(Contact contact) {
        if (contact.getId() == 0) {
            return dao.createContact(contact);
        } else {
            return dao.updateContact(contact);
        }
    }

    @Override
    public boolean deleteContact(Contact contact) {
        return dao.deleteContact(contact);
    }

    @Override
    public Contact getContactById(long id) {
        return dao.getContactById(id);
    }

    @Override
    public List<Contact> getAllContacts() {
        return dao.getAllContacts();
    }
}
