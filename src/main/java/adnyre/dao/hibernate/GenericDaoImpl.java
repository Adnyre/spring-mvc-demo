package adnyre.dao.hibernate;

import adnyre.exception.DaoException;
import adnyre.model.BaseEntity;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("genericDao")
@Profile("hibernate")
@Transactional
public class GenericDaoImpl<T extends BaseEntity>
        implements GenericDao<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericDaoImpl.class);

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GenericDaoImpl.class);
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T create(T t) throws DaoException {
        if (find(t.getId()) == null) {
            return this.entityManager.merge(t);
        }
        return null;
    }

    @Override
    public T find(int id) throws DaoException {
        return this.entityManager.find(entityClass, id);
    }

    @Override
    public T update(T t) throws DaoException {
        return (find(t.getId()) != null) ? entityManager.merge(t) : null;
    }


    @Override
    public void delete(T t) throws DaoException {
        this.entityManager.remove(this.entityManager.merge(t));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DaoException {
        String hql = String.format("FROM %s", entityClass.getName());
        LOGGER.debug(String.format("findAll HQL query for class %s: %s", entityClass, hql));
        Query query = entityManager.createQuery(hql, entityClass);
        return query.getResultList();
    }

    public static void main(String[] args) throws DaoException {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
//        ContactDaoImplHibernate cdao = context.getBean(ContactDaoImplHibernate.class);
        PhoneNumberDaoImpl pndao = context.getBean(PhoneNumberDaoImpl.class, "phoneNumberDao");

//        Contact contact = new Contact();
//        contact.setId(10);
//        contact.setFirstName("bbb");
//        contact.setLastName("bbb");

//        System.out.println(dao.update(contact));
//        System.out.println(dao.findAll());
//        dao.delete(contact);
//        System.out.println(cdao.create(contact));
        LOGGER.debug(EntityManager.class.getProtectionDomain()
                .getCodeSource()
                .getLocation());
        System.out.println(pndao.findByNumberType("024-111-11-11", "home"));
    }

}
