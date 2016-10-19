package adnyre.dao;

import adnyre.model.PhoneNumber;

import java.io.Serializable;
import java.util.List;

public interface PhoneNumberDao {

    PhoneNumber createPhoneNumber(PhoneNumber phoneNumber, long contactId) throws DaoException;

    List<PhoneNumber> createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DaoException;

    PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) throws DaoException;

    List<PhoneNumber> updatePhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DaoException;

    boolean deletePhoneNumber(PhoneNumber phoneNumber) throws DaoException;

    PhoneNumber getPhoneNumberById(long id) throws DaoException;

    List<PhoneNumber> getAllPhoneNumbers(long contactId) throws DaoException;

    void deleteAllPhoneNumbers(long contactId) throws DaoException;

}
