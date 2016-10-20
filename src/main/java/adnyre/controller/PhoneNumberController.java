package adnyre.controller;

import adnyre.model.PhoneNumber;
import adnyre.service.PhoneNumberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/pn")
public class PhoneNumberController {

    private static final Logger LOGGER = Logger.getLogger(ContactController.class);

    @Autowired
    private PhoneNumberService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable("id") int id) {
        LOGGER.debug("Finding phone number by id: " + id);
        PhoneNumber phoneNumber = service.getPhoneNumberById(id);
        if (phoneNumber == null) {
            LOGGER.debug("No contact found with id: " + id);
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(phoneNumber, OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {
        LOGGER.debug("Finding all phone numbers");
        return new ResponseEntity<>(service.getAllPhoneNumbers(), OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PhoneNumber> createPhoneNumber(@RequestBody PhoneNumber phoneNumber) {
        LOGGER.debug("Trying to save phone number: " + phoneNumber);
        return new ResponseEntity<>(service.createPhoneNumber(phoneNumber), OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PhoneNumber> updatePhoneNumber(@RequestBody PhoneNumber phoneNumber) {
        LOGGER.debug("Trying to update phone number: " + phoneNumber);
        return new ResponseEntity<>(service.updatePhoneNumber(phoneNumber), OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> deletePhoneNumber(@RequestBody PhoneNumber phoneNumber) {
        LOGGER.debug("Trying to delete phone number: " + phoneNumber);
        service.deletePhoneNumber(phoneNumber);
        return new ResponseEntity<>("<h2>phone number deleted</h2>", OK);
    }
}
