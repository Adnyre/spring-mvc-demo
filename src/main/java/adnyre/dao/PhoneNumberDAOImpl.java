package adnyre.dao;

import adnyre.model.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("phoneNumberDao")
public class PhoneNumberDAOImpl implements PhoneNumberDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

//    public void setDataSource(DataSource dataSource) {
//        this.jdbcTemplate = new org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate(dataSource);
//    }

//    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public boolean createPhoneNumber(PhoneNumber phoneNumber, long contactId) {
        String SQL = "INSERT INTO phone_numbers (contact_id, type, number) VALUES (:contact_id, :type, :number)";
        Map<String, Object> namedParameters = new HashMap<String, Object>() {
            {
                put("contact_id", contactId);
                put("type", phoneNumber.getType());
                put("number", phoneNumber.getNumber());
            }
        };
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public void createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId) {
        String SQL = "INSERT INTO phone_numbers (contact_id, type, number) VALUES (:contact_id, :type, :number)";
        HashMap[] batchValues = new HashMap[phoneNumbers.size()];
        for (int i = 0; i < batchValues.length; i++) {
            batchValues[i].put("contact_id", contactId);
            batchValues[i].put("type", phoneNumbers.get(i).getType());
            batchValues[i].put("number", phoneNumbers.get(i).getNumber());
        }
        jdbcTemplate.batchUpdate(SQL, batchValues);
    }

    @Override
    public boolean updatePhoneNumber(PhoneNumber phoneNumber) {
        String SQL = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public void updatePhoneNumbers(List<PhoneNumber> phoneNumbers) {
        String SQL = "UPDATE phone_numbers SET type=:type, number=:number WHERE id=:id";
        SqlParameterSource[] batchValues = new SqlParameterSource[phoneNumbers.size()];
        for (int i = 0; i < batchValues.length; i++) {
            batchValues[i] = new BeanPropertySqlParameterSource(phoneNumbers.get(i));
        }
        jdbcTemplate.batchUpdate(SQL, batchValues);
    }

    @Override
    public boolean deletePhoneNumber(PhoneNumber phoneNumber) {
        String SQL = "DELETE FROM phone_numbers WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneNumber);
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public void deletePhoneNumbers(List<PhoneNumber> phoneNumbers) {
        String SQL = "DELETE FROM phone_numbers WHERE id=:id";
        SqlParameterSource[] batchValues = new SqlParameterSource[phoneNumbers.size()];
        for (int i = 0; i < batchValues.length; i++) {
            batchValues[i] = new BeanPropertySqlParameterSource(phoneNumbers.get(i));
        }
        jdbcTemplate.batchUpdate(SQL, batchValues);
    }

    @Override
    public PhoneNumber getPhoneNumberById(long id) {
        String SQL = "SELECT * FROM phone_numbers WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);
        PhoneNumber phoneNumber = jdbcTemplate.queryForObject(SQL, namedParameters, new PhoneNumberMapper());
        return phoneNumber;
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers(long contactId) {
        String SQL = "SELECT * FROM phone_numbers WHERE contact_id = :contact_id";
        Map<String, Long> namedParameters = Collections.singletonMap("contact_id", contactId);
        List<PhoneNumber> phoneNumbers = jdbcTemplate.query(SQL, namedParameters, new PhoneNumberMapper());
        return phoneNumbers;
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

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        PhoneNumberDAO dao = context.getBean("phoneNumberDao", PhoneNumberDAO.class);
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
