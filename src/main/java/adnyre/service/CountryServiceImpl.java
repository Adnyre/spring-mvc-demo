package adnyre.service;

import adnyre.pojo.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("prod")
public class CountryServiceImpl implements CountryService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CloseableHttpClient client;

    private static final Logger LOGGER = Logger.getLogger(CountryServiceImpl.class);

    private static final String BASE_URL = "https://restcountries.eu/rest/v1";

    @Override
    public boolean checkIfCodeExists(String code) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/alpha/" + code);
            LOGGER.debug(request);
            HttpResponse response = client.execute(request);
            return response.getStatusLine().getStatusCode() != HttpStatus.SC_NOT_FOUND;
        } catch (Exception e) {
            LOGGER.error("IOException in getCountryJSON()");
            throw new ServiceException(e);
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/alpha/" + code);
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
        HttpGet request = new HttpGet(BASE_URL + "/all");
        try (CloseableHttpResponse response = client.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                List<Country> codes = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Country>>() {
                });
                return codes.stream().map(Country::getAlpha3Code).collect(Collectors.toList());
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("IOException in getCountryJSON()");
            throw new ServiceException(e);
        }
    }
}
