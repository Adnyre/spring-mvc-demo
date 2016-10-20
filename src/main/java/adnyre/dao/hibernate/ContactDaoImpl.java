package adnyre.dao.hibernate;

import adnyre.dao.ContactDao;
import adnyre.dao.GenericDao;
import adnyre.model.Contact;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // ("contactDao")
@Profile("hibernate")
@Transactional
public class ContactDaoImpl extends GenericDaoImpl<Contact> implements ContactDao {
}
