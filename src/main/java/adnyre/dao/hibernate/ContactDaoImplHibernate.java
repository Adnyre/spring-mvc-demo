package adnyre.dao.hibernate;

import adnyre.dao.ContactDao;
import adnyre.dao.DaoException;
import adnyre.dao.jdbc.ContactDaoImpl;
import adnyre.model.Contact;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("contactDao")
@Profile("hibernate")
@Transactional
public class ContactDaoImplHibernate implements ContactDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Contact createContact(Contact contact) throws DaoException {

        String hql = ""; //TODO
        entityManager.createQuery(hql);
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteContact(Contact contact) throws DaoException {
        String hql = "DELETE FROM Contact WHERE id = :id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", contact.getId());
        int res = query.executeUpdate();
        return res > 0;
    }

    @Override
    public Contact getContactById(long id) throws DaoException {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() throws DaoException {
        return null;
    }

    public static void main(String[] args) throws DaoException {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactDao dao = (ContactDao) context.getBean("contactDao");

        Contact contact = new Contact();
        contact.setId(9);
        contact.setFirstName("");
        contact.setLastName("");
        System.out.println(dao.deleteContact(contact));
    }
}
