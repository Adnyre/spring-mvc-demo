package adnyre.dao;

import adnyre.exception.DaoException;
import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberDao extends GenericDao<PhoneNumber> {

    PhoneNumber create(PhoneNumber phoneNumber, int contactId) throws DaoException;

    List<PhoneNumber> createAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException;

//    PhoneNumber update(PhoneNumber phoneNumber) throws DaoException;

    List<PhoneNumber> updateAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException;

//    void delete(PhoneNumber phoneNumber) throws DaoException;

//    PhoneNumber find(int id) throws DaoException;

    List<PhoneNumber> findAll(int contactId) throws DaoException;

//    List<PhoneNumber> findAll() throws DaoException;

    void deleteAll(int contactId) throws DaoException;

    PhoneNumber findByNumberType(String number, String type);

}
