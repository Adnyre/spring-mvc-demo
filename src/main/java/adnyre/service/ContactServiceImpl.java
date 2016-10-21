package adnyre.service;

import adnyre.dao.hibernate.GenericDao;
import adnyre.exception.DaoException;
import adnyre.model.Contact;
import adnyre.pojo.Country;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactServiceImpl.class);

    @Autowired
    @Qualifier("contactDao")
    private GenericDao<Contact> dao;

    @Autowired
    private CountryService countryService;

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
    public ContactDto getContactById(int id) throws ServiceException {
        try {
            LOGGER.debug("Getting contact by id: " + id);
            Contact contact = dao.find(id);
            String countryCode = contact.getCountryCode();
            Country country = countryService.getCountryByCode(countryCode);
            ContactDto dto = convert(contact);
            dto.setCountry(country);
            return dto;
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::find", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ContactDto> getAllContacts() throws ServiceException {
        //TODO
        try {
            LOGGER.debug("Getting all contacts");
            List<Contact> contacts = dao.findAll();
            return contacts.stream().map(x -> {
                ContactDto z = convert(x);
                z.setCountry(countryService.getCountryByCode(x.getCountryCode()));
                return z;
            }).collect(Collectors.toList());
        } catch (DaoException e) {
            LOGGER.error("DaoException in ContactServiceImpl::findAll", e);
            throw new ServiceException(e);
        }
    }

    private ContactDto convert(Contact contact) {
        ContactDto dto = new ContactDto();
        dto.setLastName(contact.getLastName());
        dto.setFirstName(contact.getFirstName());
        dto.setPhoneNumbers(contact.getPhoneNumbers());
        dto.setCountryCode(contact.getCountryCode());
        dto.setId(contact.getId());
        return dto;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactService service = context.getBean(ContactService.class, "contactService");
        System.out.println(service.getAllContacts());
    }
}
