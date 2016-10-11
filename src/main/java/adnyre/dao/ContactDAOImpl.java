package adnyre.dao;

import adnyre.model.Contact;
import adnyre.model.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("dao")
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private PhoneNumberDAO phoneNumberDAO;

    @Override
    public boolean createContact(Contact contact) {
        String SQL = "INSERT INTO contacts (first_name, last_name) VALUES (:firstName, :lastName)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(SQL, namedParameters, keyHolder);
        Number primaryKey = (Number) keyHolder.getKeys().get("id");
        contact.setId(primaryKey.longValue());
        phoneNumberDAO.createPhoneNumbers(contact.getPhoneNumbers(), contact.getId());
        return update > 0;
    }

    @Override
    public boolean updateContact(Contact contact) {
        String SQL = "UPDATE contacts SET first_name=:firstName, last_name=:lastName WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        int update = jdbcTemplate.update(SQL, namedParameters);
        phoneNumberDAO.updatePhoneNumbers(contact.getPhoneNumbers());
        return update > 0;
    }

    @Override
    public boolean deleteContact(Contact contact) {
        String SQL = "DELETE FROM contacts WHERE id = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        phoneNumberDAO.deletePhoneNumbers(contact.getPhoneNumbers());
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public Contact getContactById(long id) {
        String SQL = "SELECT * FROM contacts WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);
        Contact contact = jdbcTemplate.queryForObject(SQL, namedParameters, new ContactMapper());
        contact.setPhoneNumbers(phoneNumberDAO.getAllPhoneNumbers(id));
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        String SQL = "SELECT * FROM contacts";
        List<Contact> contacts = jdbcTemplate.query(SQL, new ContactMapper());
        for (Contact contact : contacts){
            List<PhoneNumber> phoneNumbers = phoneNumberDAO.getAllPhoneNumbers(contact.getId());
            contact.setPhoneNumbers(phoneNumbers);
        }
        return contacts;
    }

    private static class ContactMapper implements RowMapper<Contact> {
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact res = new Contact();
            res.setId(rs.getInt("id"));
            res.setFirstName(rs.getString("first_name"));
            res.setLastName(rs.getString("last_name"));
            return res;
        }
    }

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ContactDAOImpl.class);
//        ContactDAO dao = context.getBean(ContactDAO.class);
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactDAO dao = context.getBean("dao", ContactDAO.class);
//        System.out.println(dao.getContactById(1));
        Contact contact = new Contact();
        contact.setId(0);
        contact.setFirstName("Leonid");
        contact.setLastName("Kuchma");
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("888888");
        phoneNumber.setType("hbfdekj");
        contact.addPhoneNumber(phoneNumber);
        System.out.println(dao.createContact(contact));
//        System.out.println(dao.deleteContact(contact));
//        System.out.println(dao.getAllContacts());
    }
}
