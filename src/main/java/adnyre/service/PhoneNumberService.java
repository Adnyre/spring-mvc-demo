package adnyre.service;

import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {
    boolean createOrUpdatePhoneNumber(PhoneNumber phoneNumber, long contactId) throws ServiceException;

    boolean deletePhoneNumber(PhoneNumber phoneNumber) throws ServiceException;

    PhoneNumber getPhoneNumberById(long id) throws ServiceException;

    List<PhoneNumber> getAllPhoneNumbers(long contactId) throws ServiceException;
}
