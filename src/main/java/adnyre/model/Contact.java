package adnyre.model;

import java.util.Set;

public class Contact {
    private long id;
    private String firstName;
    private String lastName;
    private Set<PhoneNumber> phoneNumbers;

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
