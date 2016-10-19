package adnyre.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, Long> {
    T create(T t) throws DaoException;
    T find(Long id) throws DaoException;
    T update(T t) throws DaoException;
    void delete(T t) throws DaoException;
    List<T> findAll() throws DaoException;
}
