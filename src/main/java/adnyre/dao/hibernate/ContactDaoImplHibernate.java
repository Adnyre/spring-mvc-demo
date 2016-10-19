package adnyre.dao.hibernate;

import adnyre.model.Contact;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("contactDao")
@Profile("hibernate")
@Transactional
public class ContactDaoImplHibernate extends GenericDaoImplHibernate<Contact>{
}
