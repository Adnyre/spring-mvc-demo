package adnyre.dao;

import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberDAO {

    boolean createPhoneNumber(PhoneNumber phoneNumber, long contactId) throws DAOException;

    void createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DAOException;

    boolean updatePhoneNumber(PhoneNumber phoneNumber) throws DAOException;

    void updatePhoneNumbers(List<PhoneNumber> phoneNumbers) throws DAOException;

    boolean deletePhoneNumber(PhoneNumber phoneNumber) throws DAOException;

    PhoneNumber getPhoneNumberById(long id) throws DAOException;

    List<PhoneNumber> getAllPhoneNumbers(long contactId) throws DAOException;

    void deleteAllPhoneNumbers(long contactId) throws DAOException;
}
