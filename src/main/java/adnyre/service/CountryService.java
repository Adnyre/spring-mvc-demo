package adnyre.service;

import adnyre.pojo.Country;

import java.util.List;

public interface CountryService {
    boolean checkIfCodeExists(String code);

    Country getCountryByCode(String code);

    List<String> getAllCodes();
}
