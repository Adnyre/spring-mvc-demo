package adnyre.controller;

import adnyre.service.CountryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/codes")
public class CountryController {

    private static final Logger LOGGER = Logger.getLogger(ContactController.class);

    @Autowired
    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<String>> getAllCodes() {
        LOGGER.debug("Finding all codes");
        return new ResponseEntity<>(countryService.getAllCodes(), OK);
    }
}
