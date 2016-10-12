package adnyre.controller;

import adnyre.model.Contact;
import adnyre.service.ContactService;
import adnyre.service.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService service;

    @RequestMapping(method = RequestMethod.GET) //, produces = "application/json"
    @ResponseBody
    public Contact printContact(@RequestParam int id) {
        return service.getContactById(id);
    }
}
