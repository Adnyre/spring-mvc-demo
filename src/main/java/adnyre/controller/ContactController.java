package adnyre.controller;

import adnyre.model.Contact;
import adnyre.service.ContactService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private static final Logger LOGGER = Logger.getLogger(ContactController.class);

    @Autowired
    private ContactService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Contact> getContact(@PathVariable("id") long id) {
        LOGGER.debug("Finding contact by id: " + id);
        Contact contact = service.getContactById(id);
        if (contact == null) {
            LOGGER.debug("No contact found with id: " + id);
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(contact, OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Contact>> getAllContacts() {
        LOGGER.debug("Finding all contacts");
        return new ResponseEntity<>(service.getAllContacts(), OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        LOGGER.debug("Trying to save contact: " + contact);
        return new ResponseEntity<>(service.createContact(contact), OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        LOGGER.debug("Trying to update contact: " + contact);
        return new ResponseEntity<>(service.updateContact(contact), OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> deleteContact(@RequestBody Contact contact) {
        LOGGER.debug("Trying to delete contact: " + contact);
        if (service.deleteContact(contact))
            return new ResponseEntity<>("<h2>contact deleted</h2>", OK);
        else
            LOGGER.debug("Unable to delete contact: " + contact);
        return new ResponseEntity<>(NOT_FOUND);
    }
}
