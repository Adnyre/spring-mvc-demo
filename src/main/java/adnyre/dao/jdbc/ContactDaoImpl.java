package adnyre.dao.jdbc;

import adnyre.exception.DaoException;
import adnyre.model.Contact;
import adnyre.model.PhoneNumber;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//@Repository("contactDao")
//@Profile("jdbc")
//@Transactional
public class ContactDaoImpl implements ContactDao {

    private static final Logger LOGGER = Logger.getLogger(ContactDaoImpl.class);

    //    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //    @Autowired
    private PhoneNumberDao phoneNumberDAO;

    @Override
    public Contact create(Contact contact) throws DaoException {
        String SQL = "INSERT INTO contacts (first_name, last_name) VALUES (:firstName, :lastName)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            LOGGER.debug("Inserting contact: " + contact);
            int update = jdbcTemplate.update(SQL, namedParameters, keyHolder);
            Number primaryKey = (Number) keyHolder.getKeys().get("id");
            contact.setId(primaryKey.intValue());
            LOGGER.debug("Creating phone numbers for contact: " + contact);
            phoneNumberDAO.createAll(contact.getPhoneNumbers(), contact.getId());
            if (update > 0) return contact;
            else return null;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in ContactDaoImpl::create", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Contact update(Contact contact) throws DaoException {
        String SQL = "UPDATE contacts SET first_name=:firstName, last_name=:lastName WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        try {
            int update = jdbcTemplate.update(SQL, namedParameters);
            phoneNumberDAO.updateAll(contact.getPhoneNumbers(), contact.getId());
            if (update > 0) return contact;
            else return null;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in ContactDaoImpl::update", e);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Contact contact) throws DaoException {
        String SQL = "DELETE FROM contacts WHERE id = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        try {
            phoneNumberDAO.deleteAll((int) namedParameters.getValue("id"));
            jdbcTemplate.update(SQL, namedParameters);
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in ContactDaoImpl::delete", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Contact find(int id) throws DaoException {
        String SQL = "SELECT contacts.id AS contact_id, first_name, last_name, phone_numbers.id AS phone_number_id, type, number" +
                " FROM contacts LEFT JOIN phone_numbers ON contacts.id = phone_numbers.contact_id WHERE contacts.id = :id";
        try {
            List<Contact> contacts = jdbcTemplate.query(SQL, Collections.singletonMap("id", id), new ContactResultSetExtractor());
            if (contacts.size() == 0)
                return null;
            else
                LOGGER.debug("returning contact: " + contacts.get(0));
            return contacts.get(0);
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in ContactDaoImpl::find", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Contact> findAll() throws DaoException {
        String SQL = "SELECT contacts.id AS contact_id, first_name, last_name, phone_numbers.id AS phone_number_id, type, number" +
                " FROM contacts LEFT JOIN phone_numbers ON contacts.id = phone_numbers.contact_id";
        try {
            return jdbcTemplate.query(SQL, new ContactResultSetExtractor());
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException in ContactDaoImpl::findAll", e);
            throw new DaoException(e);
        }
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

    private static class ContactResultSetExtractor implements ResultSetExtractor<List<Contact>> {
        @Override
        public List<Contact> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Integer, Contact> map = new HashMap<>();
            Contact contact = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("contact_id");
                LOGGER.debug("contact id: " + resultSet.getLong("contact_id"));
                if (!map.containsKey(id)) {
                    contact = new Contact();
                    contact.setId(id);
                    contact.setFirstName(resultSet.getString("first_name"));
                    LOGGER.debug("contact first name: " + resultSet.getString("first_name"));
                    contact.setLastName(resultSet.getString("last_name"));
                    map.put(id, contact);
                } else {
                    contact = map.get(id);
                }
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setId(resultSet.getInt("phone_number_id"));
                phoneNumber.setNumber(resultSet.getString("number"));
                phoneNumber.setType(resultSet.getString("type"));
                contact.addPhoneNumber(phoneNumber);
            }
            return new ArrayList<>(map.values());
        }
    }

    public static void main(String[] args) throws DaoException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ContactDaoImpl.class);
//        ContactDao dao = context.getBean(ContactDao.class);
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/application-context.xml");
        ContactDao dao = context.getBean("dao", ContactDao.class);
//        System.out.println(dao.find(1));
        Contact contact = new Contact();
        contact.setId(0);
        contact.setFirstName("Leonid");
        contact.setLastName("Kuchma");
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("888888");
        phoneNumber.setType("hbfdekj");
        contact.addPhoneNumber(phoneNumber);
        System.out.println(dao.create(contact));
//        System.out.println(dao.delete(contact));
//        System.out.println(dao.findAll());
    }
}
