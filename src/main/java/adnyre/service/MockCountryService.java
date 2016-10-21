package adnyre.service;

import adnyre.pojo.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("mock")
public class MockCountryService implements CountryService {

    private static List<Country> COUNTRIES;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean checkIfCodeExists(String code) {
        return COUNTRIES.stream().map(Country::getAlpha3Code).anyMatch(x -> x.equalsIgnoreCase(code));
    }

    @Override
    public Country getCountryByCode(String code) {
        Optional<Country> opt = COUNTRIES.stream().filter(x -> x.getAlpha3Code().equalsIgnoreCase(code)).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public List<String> getAllCodes() {
        return COUNTRIES.stream().map(Country::getAlpha3Code).collect(Collectors.toList());
    }

    public void worker() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        COUNTRIES = mapper.readValue(this.getClass().getResourceAsStream("countries"), new TypeReference<List<Country>>() {
                        });
                        Thread.sleep(5000*60);
                    } catch (IOException|InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
