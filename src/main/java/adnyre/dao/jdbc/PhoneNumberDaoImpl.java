package adnyre.dao.jdbc;

import adnyre.exception.DaoException;
import adnyre.model.PhoneNumber;
import org.apache.log4j.Logger;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Repository("phoneNumberDao")
//@Profile("jdbc")
//@Transactional
public class PhoneNumberDaoImpl implements PhoneNumberDao {

    private static final Logger LOGGER = Logger.getLogger(PhoneNumberDaoImpl.class);

    //    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public PhoneNumber create(PhoneNumber phoneNumber, int contactId) throws DaoException {
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
            phoneNumber.setId(primaryKey.intValue());
            return phoneNumber;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::create", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> createAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException {
        //TODO!!!
        try {
            for (PhoneNumber phoneNumber : phoneNumbers) {
                create(phoneNumber, contactId);
            }
            return phoneNumbers;
        } catch (Exception e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::createAll", e);
            throw new DaoException(e);
        }
    }

    @Override
    public PhoneNumber update(PhoneNumber phoneNumber) throws DaoException {
        String SQL = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        try {
            jdbcTemplate.update(SQL, namedParameters);
            return phoneNumber;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::update", e);
            throw new DaoException(e);
        }
    }

    //TODO!!!
    @Override
    public List<PhoneNumber> updateAll(List<PhoneNumber> phoneNumbers, int contactId) throws DaoException {
        try {
            List<Integer> phoneNumberIdsInMemory = phoneNumbers.stream().map(PhoneNumber::getId).collect(Collectors.toList());
            String query = "SELECT id FROM phone_numbers WHERE contact_id = :contact_id";
            Map<String, Integer> namedParameters = Collections.singletonMap("contact_id", contactId);
            List<Long> persistedPhoneNumberIds = jdbcTemplate.query(query, namedParameters, (resultSet, i) -> resultSet.getLong("id"));
            //TODO!!
            StringBuilder sb = new StringBuilder("(");
            for (long phoneNumberId : phoneNumberIdsInMemory) {
                sb.append(phoneNumberId + ",");
            }

            sb.delete(sb.length() - 1, sb.length()).append(")");
            String delete = "DELETE FROM phone_numbers WHERE contactId=:contactId AND NOT id IN " + sb.toString();
            namedParameters = Collections.singletonMap("contactId", contactId);
            jdbcTemplate.update(delete, namedParameters);

            String insert = "INSERT INTO phone_numbers (contact_id, type, number) VALUES (:contact_id, :type, :number)";
            List<PhoneNumber> phoneNumbersToAdd = phoneNumbers.stream()
                    .filter(x -> phoneNumberIdsInMemory.contains(x.getId()) && !persistedPhoneNumberIds.contains(x.getId()))
                    .collect(Collectors.toList());
            Map<String, Object>[] batchValues = (Map<String, Object>[]) new Map[phoneNumbersToAdd.size()];
            for (int i = 0; i < phoneNumbersToAdd.size(); i++) {
                batchValues[i] = new HashMap<>();
                batchValues[i].put("contact_id", contactId);
                batchValues[i].put("type", phoneNumbersToAdd.get(i).getType());
                batchValues[i].put("number", phoneNumbersToAdd.get(i).getNumber());
            }
            jdbcTemplate.batchUpdate(insert, batchValues);

            String update = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
            List<PhoneNumber> phoneNumbersToUpdate = phoneNumbers.stream()
                    .filter(x -> phoneNumberIdsInMemory.contains(x.getId()) && persistedPhoneNumberIds.contains(x.getId()))
                    .collect(Collectors.toList());
            batchValues = (Map<String, Object>[]) new Map[phoneNumbersToUpdate.size()];
            for (int i = 0; i < phoneNumbersToUpdate.size(); i++) {
                batchValues[i] = new HashMap<>();
                batchValues[i].put("id", phoneNumbersToUpdate.get(i).getId());
                batchValues[i].put("type", phoneNumbersToUpdate.get(i).getType());
                batchValues[i].put("number", phoneNumbersToUpdate.get(i).getNumber());
            }
            jdbcTemplate.batchUpdate(update, batchValues);
            return phoneNumbers;
        } catch (Exception e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::updateAll", e);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(PhoneNumber phoneNumber) throws DaoException {
        String SQL = "DELETE FROM phone_numbers WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        try {
            jdbcTemplate.update(SQL, namedParameters);
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::delete", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PhoneNumber create(PhoneNumber phoneNumber) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PhoneNumber find(int id) throws DaoException {
        String SQL = "SELECT * FROM phone_numbers WHERE id = :id";
        Map<String, Integer> namedParameters = Collections.singletonMap("id", id);
        try {
            return jdbcTemplate.queryForObject(SQL, namedParameters, new PhoneNumberMapper());
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::find", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<PhoneNumber> findAll(int contactId) throws DaoException {
        String SQL = "SELECT * FROM phone_numbers WHERE contact_id = :contact_id";
        Map<String, Integer> namedParameters = Collections.singletonMap("contact_id", contactId);
        List<PhoneNumber> phoneNumbers = null;
        try {
            phoneNumbers = jdbcTemplate.query(SQL, namedParameters, new PhoneNumberMapper());
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::findAll", e);
            throw new DaoException(e);
        }
        return phoneNumbers;
    }

    @Override
    public void deleteAll(int contactId) throws DaoException {
        String SQL = "DELETE FROM phone_numbers WHERE contact_id = :contact_id";
        Map<String, Integer> namedParameters = Collections.singletonMap("contact_id", contactId);
        try {
            jdbcTemplate.update(SQL, namedParameters);
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in PhoneNumberDaoImpl::findAll", e);
            throw new DaoException(e);
        }
    }


    @Override
    public PhoneNumber findByNumberType(String number, String type) {
        throw new UnsupportedOperationException();
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
//        System.out.println(dao.find(1));
        PhoneNumber pn = new PhoneNumber();
        pn.setId(5);
        pn.setType("home");
        pn.setNumber("002-999-000-11");
        System.out.println(dao.findAll(1));
//        System.out.println(dao.delete(contact));
//        System.out.println(dao.findAll());
    }
}
