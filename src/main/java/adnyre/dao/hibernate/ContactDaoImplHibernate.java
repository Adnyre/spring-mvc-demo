package adnyre.dao.hibernate;

import adnyre.dao.ContactDao;
import adnyre.dao.DaoException;
import adnyre.model.Contact;
import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("contactDao")
@Profile("hibernate")
@Transactional
public class ContactDaoImplHibernate extends GenericDaoImplHibernate<Contact, Long>{

    private static final Logger LOGGER = Logger.getLogger(ContactDaoImplHibernate.class);

    @PersistenceContext
    private EntityManager entityManager;


//    @Override
//    public Contact create(Contact contact) throws DaoException {
//        entityManager.persist(contact);
//        return contact;
//    }
//
//    @Override
//    public Contact update(Contact contact) throws DaoException {
//        return (find(contact.getId()) != null) ? entityManager.merge(contact) : null;
//    }
//
//    @Override
//    public void delete(Contact contact) throws DaoException {
//        entityManager.remove(entityManager.contains(contact) ? contact : entityManager.merge(contact));
//    }
//
//    @Override
//    public Contact find(Long id) throws DaoException {
//        return entityManager.<Contact>find(Contact.class, id);
//    }

//    @Override
    public List<Contact> findAll() throws DaoException {
        return null;
    }

    public static void main(String[] args) throws DaoException {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactDaoImplHibernate dao = (ContactDaoImplHibernate) context.getBean("contactDao");

        Contact contact = new Contact();
        contact.setId(6L);
        contact.setFirstName("test");
        contact.setLastName("test");

//        System.out.println(dao.update(contact));
        System.out.println(dao.find(1L));
//        dao.delete(contact);
    }
}
