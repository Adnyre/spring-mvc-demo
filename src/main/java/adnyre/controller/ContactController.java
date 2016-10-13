package adnyre.controller;

import adnyre.model.Contact;
import adnyre.service.ContactService;
import adnyre.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private static final Logger LOGGER = Logger.getLogger(ContactController.class);

    @Autowired
    private ContactService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Contact> getContact(@PathVariable("id") long id) {
        try {
            LOGGER.debug("Finding contact by id: " + id);
            Contact contact = service.getContactById(id);
            if (contact == null) {
                LOGGER.debug("No contact found wit id: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException in ContactController::getContact", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            return new ResponseEntity<>(service.getAllContacts(), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException in ContactController::getAllContacts", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) {
        try {
            LOGGER.debug("Trying to save contact: " + contact);
            return new ResponseEntity<>(service.createOrUpdateContact(contact), HttpStatus.CREATED);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException in ContactController::saveContact", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> deleteContact(@RequestBody Contact contact) {
        try {
            LOGGER.debug("Trying to delete contact: " + contact);
            if (service.deleteContact(contact))
                return new ResponseEntity<>("<h2>contact deleted</h2>", HttpStatus.OK);
            else
                LOGGER.debug("Unable to delete contact: " + contact);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException in ContactController::deleteContact", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
