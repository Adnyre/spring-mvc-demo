package adnyre.service;

import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {
    boolean createOrUpdatePhoneNumber(PhoneNumber phoneNumber, long contactId);

    boolean deletePhoneNumber(PhoneNumber phoneNumber);

    PhoneNumber getPhoneNumberById(long id);

    List<PhoneNumber> getAllPhoneNumbers(long contactId);
}
