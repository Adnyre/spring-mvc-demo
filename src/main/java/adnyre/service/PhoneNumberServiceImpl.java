package adnyre.service;

import adnyre.dao.PhoneNumberDAO;
import adnyre.model.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberDAO dao;

    @Override
    public boolean createOrUpdatePhoneNumber(PhoneNumber phoneNumber, long contactId) {
        if (phoneNumber.getId() == 0) {
            return dao.createPhoneNumber(phoneNumber, contactId);
        } else {
            return dao.updatePhoneNumber(phoneNumber);
        }
    }

    @Override
    public boolean deletePhoneNumber(PhoneNumber phoneNumber) {
        return dao.deletePhoneNumber(phoneNumber);
    }

    @Override
    public PhoneNumber getPhoneNumberById(long id) {
        return dao.getPhoneNumberById(id);
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(long contactId) {
        return dao.getAllPhoneNumbers(contactId);
    }
}
