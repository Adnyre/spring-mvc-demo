package adnyre.dao.hibernate;

import adnyre.dao.PhoneNumberDao;
import adnyre.exception.DaoException;
import adnyre.model.PhoneNumber;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository // ("phoneNumberDao")
@Profile("hibernate")
@Transactional
public class PhoneNumberDaoImpl extends GenericDaoImpl<PhoneNumber> implements PhoneNumberDao {
    @Override
    public PhoneNumber create(PhoneNumber phoneNumber, int contactId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PhoneNumber> createAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PhoneNumber> updateAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PhoneNumber> findAll(int contactId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(int contactId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public PhoneNumber findByNumberType(String number, String type) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PhoneNumber> criteriaQuery = builder.createQuery(PhoneNumber.class);
        Root<PhoneNumber> root = criteriaQuery.from(PhoneNumber.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("number"), number),
                            builder.equal(root.get("type"), type));
        TypedQuery<PhoneNumber> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
