package adnyre.dao.hibernate;

import adnyre.dao.DaoException;
import adnyre.dao.GenericDao;
import adnyre.model.BaseEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository("genericDao")
@Profile("hibernate")
@Transactional
public class GenericDaoImplHibernate<T extends BaseEntity, Long>
        implements GenericDao<T, Long> {

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
        this.entityManager.persist(t);
        return t;
    }

    @Override
    public T find(Long id) throws DaoException {
        return this.entityManager.find(entityClass, id);
    }

    @Override
    public T update(T t) throws DaoException {
        return (find((Long) t.getId()) != null) ? entityManager.merge(t) : null;
    }

    @Override
    public void delete(T t) throws DaoException{
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }

    @Override
    public List<T> findAll() throws DaoException {
        return null;
    }
}
