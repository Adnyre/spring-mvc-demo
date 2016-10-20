package adnyre.service;

import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {

    PhoneNumber createPhoneNumber(PhoneNumber phoneNumber) throws ServiceException;

    PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) throws ServiceException;

    void deletePhoneNumber(PhoneNumber phoneNumber) throws ServiceException;

    PhoneNumber getPhoneNumberById(int id) throws ServiceException;

    List<PhoneNumber> getAllPhoneNumbers() throws ServiceException;
}
