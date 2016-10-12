package adnyre.controller;

import adnyre.model.Contact;
import adnyre.service.ContactService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Contact getContact(@PathVariable("id") long id) {
        LOGGER.debug("Finding contact by id: " + id);
        return service.getContactById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Contact saveContact(@RequestBody Contact contact) {
        return service.createOrUpdateContact(contact);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "text/html")
    @ResponseBody
    public String deleteContact(@RequestBody Contact contact) {
        if (service.deleteContact(contact)) return "<h2>contact deleted</h2>";
        else return "<h2>unable to delete contact</h2>";
    }
}
