package adnyre.dao;

import adnyre.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberDAO {

        boolean createPhoneNumber(PhoneNumber phoneNumber, long contactId);

        void createPhoneNumbers(List<PhoneNumber> phoneNumbers, long contactId);

        boolean updatePhoneNumber(PhoneNumber phoneNumber);

        void updatePhoneNumbers(List<PhoneNumber> phoneNumbers);

        boolean deletePhoneNumber(PhoneNumber phoneNumber);

        void deletePhoneNumbers(List<PhoneNumber> phoneNumbers);

        PhoneNumber getPhoneNumberById(long id);

        List<PhoneNumber> getAllPhoneNumbers(long contactId);

}
