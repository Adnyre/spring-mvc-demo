package adnyre.dao.hibernate;

import adnyre.model.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("contactDao")
@Transactional
public class ContactDaoImpl extends GenericDaoImpl<Contact> {
}
