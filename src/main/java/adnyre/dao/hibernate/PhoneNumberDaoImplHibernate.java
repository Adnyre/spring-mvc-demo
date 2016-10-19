package adnyre.dao.hibernate;

import adnyre.model.PhoneNumber;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("phoneNumberDao")
@Profile("hibernate")
@Transactional
public class PhoneNumberDaoImplHibernate  extends GenericDaoImplHibernate<PhoneNumber>{
}
