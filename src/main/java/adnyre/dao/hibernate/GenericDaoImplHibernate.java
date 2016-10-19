package adnyre.dao.hibernate;

import adnyre.exception.DaoException;
import adnyre.model.BaseEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository("genericDao")
@Profile("hibernate")
@Transactional
public class GenericDaoImplHibernate<T extends BaseEntity>
        implements GenericDao<T> {

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImplHibernate() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T create(T t) throws DaoException {
        if(find(t.getId()) == null) {
            this.entityManager.persist(t);
            return t;
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
    public void delete(T t) throws DaoException{
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DaoException {
        String hql = "FROM " + entityClass.getSimpleName();
        Query query = entityManager.createQuery(hql, entityClass);
        return query.getResultList();
    }

    //criteria
    public List<T> findAll2() throws DaoException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> c = criteriaQuery.from(entityClass);
        criteriaQuery.select(c);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public static void main(String[] args) throws DaoException {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactDaoImplHibernate cdao = (ContactDaoImplHibernate) context.getBean("contactDao");
//        ContactDaoImplHibernate pndao = (ContactDaoImplHibernate) context.getBean("phoneNumberDao");

//        Contact contact = new Contact();
//        contact.setId(2);
//        contact.setFirstName("iii");
//        contact.setLastName("iii");

//        System.out.println(dao.update(contact));
//        System.out.println(dao.findAll());
//        dao.delete(contact);

        System.out.println(cdao.findAll());
    }

}
