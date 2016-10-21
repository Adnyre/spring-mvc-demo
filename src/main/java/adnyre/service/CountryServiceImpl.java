package adnyre.service;

import adnyre.pojo.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CountryServiceImpl implements CountryService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CloseableHttpClient client;

    private static final Logger LOGGER = Logger.getLogger(CountryServiceImpl.class);

    private static final String BASE_URL = "https://restcountries.eu/rest/v1/alpha/";

    @Override
    public boolean checkIfCodeExists(String code) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(BASE_URL + "/" + code);
//        request.addHeader("User-Agent", USER_AGENT);
            HttpResponse response = client.execute(request);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            LOGGER.error("IOException in getCountryJSON()");
            throw new ServiceException(e);
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(BASE_URL + "/" + code);
//        request.addHeader("User-Agent", USER_AGENT);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return mapper.readValue(response.getEntity().getContent(), Country.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("IOException in getCountryJSON()");
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getAllCodes() {
        return null;
    }
}
