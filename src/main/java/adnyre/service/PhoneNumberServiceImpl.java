package adnyre.service;

import adnyre.dao.PhoneNumberDao;
import adnyre.exception.DaoException;
import adnyre.model.PhoneNumber;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private static final Logger LOGGER = Logger.getLogger(PhoneNumberServiceImpl.class);

    @Autowired
    private PhoneNumberDao dao;

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber, int contactId) throws ServiceException {
        try {
            return dao.create(phoneNumber, contactId);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::createOrUpdatePhoneNumber", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber) throws ServiceException {
        try {
            return dao.create(phoneNumber);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::createOrUpdatePhoneNumber", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) throws ServiceException {
        try {
            return dao.update(phoneNumber);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::createOrUpdatePhoneNumber", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deletePhoneNumber(PhoneNumber phoneNumber) throws ServiceException {
        try {
            dao.delete(phoneNumber);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::delete", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(int id) throws ServiceException {
        try {
            return dao.find(id);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::find", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(int contactId) throws ServiceException {
        try {
            return dao.findAll(contactId);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::findAll", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers() throws ServiceException {
        try {
            return dao.findAll();
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::findAll", e);
            throw new ServiceException(e);
        }
    }
}
