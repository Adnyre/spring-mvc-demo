package adnyre.service;

import adnyre.model.Contact;
import adnyre.pojo.Country;

//@JsonIgnoreProperties({"notInterstingMember", "forgetThisField"})
public class ContactDto extends Contact {
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    private Country country;

    public String getAlpha3Code() {
        return country.getAlpha3Code();
    }

    public void setAlpha3Code(String alpha3Code) {
        country.setAlpha3Code(alpha3Code);
    }

    public String getAlpha2Code() {
        return country.getAlpha2Code();
    }

    public void setAlpha2Code(String alpha2Code) {
        country.setAlpha2Code(alpha2Code);
    }

    public String getName() {
        return country.getName();
    }

    public void setName(String name) {
        country.setName(name);
    }

    public String[] getTopLevelDomain() {
        return country.getTopLevelDomain();
    }

    public void setTopLevelDomain(String[] topLevelDomain) {
        country.setTopLevelDomain(topLevelDomain);
    }

    public String[] getCallingCodes() {
        return country.getCallingCodes();
    }

    public void setCallingCodes(String[] callingCodes) {
        country.setCallingCodes(callingCodes);
    }

    public String getCapital() {
        return country.getCapital();
    }

    public void setCapital(String capital) {
        country.setCapital(capital);
    }

    public String getRegion() {
        return country.getRegion();
    }

    public void setRegion(String region) {
        country.setRegion(region);
    }

    public String getSubregion() {
        return country.getSubregion();
    }

    public void setSubregion(String subregion) {
        country.setSubregion(subregion);
    }

    public String[] getAltSpellings() {
        return country.getAltSpellings();
    }

    public void setAltSpellings(String[] altSpellings) {
        country.setAltSpellings(altSpellings);
    }
}
