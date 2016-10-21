package adnyre.service;

import adnyre.pojo.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryServiceImpl implements CountryService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CloseableHttpClient client;

    private static List<String> countryCodes;

    private static final Logger LOGGER = Logger.getLogger(CountryServiceImpl.class);

    private static final String BASE_URL = "https://restcountries.eu/rest/v1/";

    @Override
    public boolean checkIfCodeExists(String code) {
        return getAllCodes().stream().allMatch(x -> x.equalsIgnoreCase(code));
    }

    @Override
    public Country getCountryByCode(String code) {
        try {
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            HttpGet request = new HttpGet(BASE_URL + "alpha/" + code);
            LOGGER.debug(request);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return mapper.readValue(response.getEntity().getContent(), Country.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("IOException in getCountryJSON()");
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getAllCodes() {
        if (countryCodes == null) {
            try {
//                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                HttpGet request = new HttpGet(BASE_URL + "all");
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    List<Country> codes = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Country>>() {
                    });
                    countryCodes = codes.stream().map(Country::getAlpha3Code).collect(Collectors.toList());
                    return countryCodes;
                } else {
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("IOException in getCountryJSON()");
                throw new ServiceException(e);
            }
        } else {
            return countryCodes;
        }
    }
}
