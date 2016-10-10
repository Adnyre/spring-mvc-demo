package adnyre.dao;

import adnyre.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean createContact(Contact contact) {
        return false;
    }

    @Override
    public boolean updateContact(Contact contact) {
        return false;
    }

    @Override
    public boolean deleteContact(Contact contact) {
        return false;
    }

    @Override
    public Contact getContactById(long id) {
        String SQL = "select * from Student where id = ?";
        Contact student = jdbcTemplate.queryForObject(SQL,
                new Object[]{10}, (rs, rowNum) -> {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setFirstName(rs.getString("first_name"));
                    contact.setLastName(rs.getString("last_name"));
                    return contact;
                });
        return student;
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }
}
