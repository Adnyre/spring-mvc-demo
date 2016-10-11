package adnyre.dao;

import adnyre.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ContactDAOImpl implements ContactDAO {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean createContact(Contact contact) {
        String SQL = "INSERT INTO contacts (first_name, last_name) VALUES (:firstName, :lastName)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public boolean updateContact(Contact contact) {
        String SQL = "UPDATE contacts SET first_name=:firstName, last_name=:lastName WHERE id=:id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public boolean deleteContact(Contact contact) {
        String SQL = "DELETE FROM contacts WHERE id = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contact);
        return jdbcTemplate.update(SQL, namedParameters) > 0;
    }

    @Override
    public Contact getContactById(long id) {
        String SQL = "SELECT * FROM contacts WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);
        Contact contact = jdbcTemplate.queryForObject(SQL, namedParameters,
                (rs, rowNum) -> {
                    Contact res = new Contact();
                    res.setId(rs.getInt("id"));
                    res.setFirstName(rs.getString("first_name"));
                    res.setLastName(rs.getString("last_name"));
                    return res;
                });
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        ContactDAO dao = (ContactDAO) context.getBean("dao");
//        System.out.println(dao.getContactById(1));
        Contact contact = new Contact();
        contact.setId(3);
        contact.setFirstName("William");
        contact.setLastName("Gates");
//        System.out.println(dao.createContact(contact));
        System.out.println(dao.deleteContact(contact));
    }
}
