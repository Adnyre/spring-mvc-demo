package adnyre.dao;

import adnyre.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    @Override
    Contact create(Contact contact) throws DaoException;

    @Override
    Contact update(Contact contact) throws DaoException;

    @Override
    void delete(Contact contact) throws DaoException;

    @Override
    Contact find(Long id) throws DaoException;

    List<Contact> findAll() throws DaoException;
}
