package adnyre.service;

import adnyre.dao.DaoException;
import adnyre.dao.PhoneNumberDao;
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
    public PhoneNumber createOrUpdatePhoneNumber(PhoneNumber phoneNumber, long contactId) throws ServiceException {
        try {
            if (phoneNumber.getId() == 0) {
                return dao.createPhoneNumber(phoneNumber, contactId);
            } else {
                return dao.updatePhoneNumber(phoneNumber);
            }
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::createOrUpdatePhoneNumber", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deletePhoneNumber(PhoneNumber phoneNumber) throws ServiceException {
        try {
            return dao.deletePhoneNumber(phoneNumber);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::deletePhoneNumber", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(long id) throws ServiceException {
        try {
            return dao.getPhoneNumberById(id);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::getPhoneNumberById", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(long contactId) throws ServiceException {
        try {
            return dao.getAllPhoneNumbers(contactId);
        } catch (DaoException e) {
            LOGGER.error("DaoException in PhoneNumberServiceImpl::getAllPhoneNumbers", e);
            throw new ServiceException(e);
        }
    }
}
