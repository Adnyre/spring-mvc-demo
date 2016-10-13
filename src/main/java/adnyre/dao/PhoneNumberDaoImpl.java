package adnyre.dao;

import adnyre.model.PhoneNumber;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("phoneNumberDao")
public class PhoneNumberDaoImpl implements PhoneNumberDao {

    private static final Logger LOGGER = Logger.getLogger(PhoneNumberDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber, long contactId) throws DaoException {
        String SQL = "INSERT INTO phone_numbers (contact_id, type, number) VALUES (:contact_id, :type, :number)";
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                new HashMap<String, Object>() {
                    {
                        put("contact_id", contactId);
                        put("type", phoneNumber.getType());
                        put("number", phoneNumber.getNumber());
                    }
                });
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(SQL, namedParameters, keyHolder);
            Number primaryKey = (Number) keyHolder.getKeys().get("id");
            phoneNumber.setId(primaryKey.longValue());
            return phoneNumber;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::createPhoneNumber", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) throws DaoException {
//        String SQL = "INSERT INTO phone_numbers (contact_id, type, number) VALUES (:contact_id, :type, :number)";
//        HashMap[] batchValues = new HashMap[phoneNumbers.size()];
//        for (int i = 0; i < batchValues.length; i++) {
//            batchValues[i] = new HashMap();
//            batchValues[i].put("contact_id", contactId);
//            batchValues[i].put("type", phoneNumbers.get(i).getType());
//            batchValues[i].put("number", phoneNumbers.get(i).getNumber());
//        }
        try {
//            LOGGER.debug(String.format("Inserting phone numbers %s for contact id %d", phoneNumbers, contactId));
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            jdbcTemplate.batchUpdate(SQL, batchValues);
//            Number primaryKey = (Number) keyHolder.getKeys().get("id");
            for (PhoneNumber phoneNumber : phoneNumbers) {
                phoneNumber = createPhoneNumber(phoneNumber, contactId);
            }
            return phoneNumbers;
        } catch (Exception e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::createPhoneNumbers", e);
            throw new DaoException(e);
        }
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) throws DaoException {
        String SQL = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        try {
            jdbcTemplate.update(SQL, namedParameters);
            return phoneNumber;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::updatePhoneNumber", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> updatePhoneNumbers(List<PhoneNumber> phoneNumbers) throws DaoException {
        String SQL = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
        SqlParameterSource[] batchValues = new SqlParameterSource[phoneNumbers.size()];
        for (int i = 0; i < batchValues.length; i++) {
            batchValues[i] = new BeanPropertySqlParameterSource(phoneNumbers.get(i));
        }
        try {
            jdbcTemplate.batchUpdate(SQL, batchValues);
            return phoneNumbers;
        } catch (Exception e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::updatePhoneNumbers", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deletePhoneNumber(PhoneNumber phoneNumber) throws DaoException {
        String SQL = "DELETE FROM phone_numbers WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        try {
            return jdbcTemplate.update(SQL, namedParameters) > 0;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::deletePhoneNumber", e);
            throw new DaoException(e);
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(long id) throws DaoException {
        String SQL = "SELECT * FROM phone_numbers WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);
        try {
            return jdbcTemplate.queryForObject(SQL, namedParameters, new PhoneNumberMapper());
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::getPhoneNumberById", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(long contactId) throws DaoException {
        String SQL = "SELECT * FROM phone_numbers WHERE contact_id = :contact_id";
        Map<String, Long> namedParameters = Collections.singletonMap("contact_id", contactId);
        List<PhoneNumber> phoneNumbers = null;
        try {
            phoneNumbers = jdbcTemplate.query(SQL, namedParameters, new PhoneNumberMapper());
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::getAllPhoneNumbers", e);
            throw new DaoException(e);
        }
        return phoneNumbers;
    }

    @Override
    public void deleteAllPhoneNumbers(long contactId) throws DaoException {
        String SQL = "DELETE FROM phone_numbers WHERE contact_id = :contact_id";
        Map<String, Long> namedParameters = Collections.singletonMap("contact_id", contactId);
        try {
            jdbcTemplate.update(SQL, namedParameters);
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::getAllPhoneNumbers", e);
            throw new DaoException(e);
        }
    }

    private static class PhoneNumberMapper implements RowMapper<PhoneNumber> {
        public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhoneNumber res = new PhoneNumber();
            res.setId(rs.getInt("id"));
            res.setType(rs.getString("type"));
            res.setNumber(rs.getString("number"));
            return res;
        }
    }

    public static void main(String[] args) throws DaoException {
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/application-context.xml");
        PhoneNumberDao dao = context.getBean("phoneNumberDao", PhoneNumberDao.class);
//        System.out.println(dao.getContactById(1));
        PhoneNumber pn = new PhoneNumber();
        pn.setId(5);
        pn.setType("home");
        pn.setNumber("002-999-000-11");
        System.out.println(dao.getAllPhoneNumbers(1));
//        System.out.println(dao.deleteContact(contact));
//        System.out.println(dao.getAllContacts());
    }
}