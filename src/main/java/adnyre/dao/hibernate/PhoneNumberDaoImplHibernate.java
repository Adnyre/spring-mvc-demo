package adnyre.dao.hibernate;

import adnyre.dao.DaoException;
import adnyre.dao.PhoneNumberDao;
import adnyre.model.PhoneNumber;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("hibernate")
@Transactional
public class PhoneNumberDaoImplHibernate implements PhoneNumberDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber, long contactId) throws DaoException {
        return null;
    }

    @Override
    public List<PhoneNumber> createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DaoException {
        return null;
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) throws DaoException {
        return null;
    }

    @Override
    public List<PhoneNumber> updatePhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DaoException {
        return null;
    }

    @Override
    public boolean deletePhoneNumber(PhoneNumber phoneNumber) throws DaoException {
        return false;
    }

    @Override
    public PhoneNumber getPhoneNumberById(long id) throws DaoException {
        return null;
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(long contactId) throws DaoException {
        return null;
    }

    @Override
    public void deleteAllPhoneNumbers(long contactId) throws DaoException {

    }
}
