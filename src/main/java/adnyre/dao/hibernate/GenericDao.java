package adnyre.dao.hibernate;

import adnyre.exception.DaoException;

import java.util.List;

public interface GenericDao<T> {
    T create(T t) throws DaoException;

    T find(int id) throws DaoException;

    T update(T t) throws DaoException;

    void delete(T t) throws DaoException;

    List<T> findAll() throws DaoException;
}
