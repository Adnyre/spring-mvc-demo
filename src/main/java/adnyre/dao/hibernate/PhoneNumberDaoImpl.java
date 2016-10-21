package adnyre.dao.hibernate;

import adnyre.exception.DaoException;
import adnyre.model.PhoneNumber;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("phoneNumberDao")
@Profile("hibernate")
@Transactional
public class PhoneNumberDaoImpl extends GenericDaoImpl<PhoneNumber> {

    private static final Logger LOGGER = Logger.getLogger(PhoneNumberDaoImpl.class);

    @Override
    public void delete(PhoneNumber phoneNumber) throws DaoException {
        LOGGER.debug("removing phone number");
        this.entityManager.remove(this.entityManager.merge(phoneNumber));
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
