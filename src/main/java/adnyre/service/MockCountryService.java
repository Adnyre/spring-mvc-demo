package adnyre.service;

import adnyre.pojo.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
@Profile("dev")
public class MockCountryService implements CountryService {

    private static List<Country> countries;

    private static final Logger LOGGER = Logger.getLogger(MockCountryService.class);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean checkIfCodeExists(String code) {
        lock.readLock().lock();
        try {
            return countries.stream().anyMatch(x -> x.getAlpha3Code().equalsIgnoreCase(code));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        lock.readLock().lock();
        try {
            Optional<Country> opt = countries.stream().filter(x -> x.getAlpha3Code().equalsIgnoreCase(code)).findFirst();
            if (opt.isPresent()) {
                return opt.get();
            }
            LOGGER.debug("returning country = null");
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<String> getAllCodes() {
        LOGGER.debug("trying to access lock in getAllCodes()");
        lock.readLock().lock();
        try {
            LOGGER.debug("getting country codes");
            return countries.stream().map(Country::getAlpha3Code).collect(Collectors.toList());
        } finally {
            LOGGER.debug("unlocking in getAllCodes()");
            lock.readLock().unlock();
        }
    }

    @PostConstruct
    private void worker() {
        LOGGER.debug("worker method invoked");
//        lock.writeLock().lock();
        Thread thread = new Thread(() -> {
            while (true) {
                try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("countries")) {
                    LOGGER.debug("locking in worker()");
                    lock.writeLock().lock();
                    try {
                        countries = mapper.readValue(in, new TypeReference<List<Country>>() {
                        });
                        LOGGER.debug("sleeping for 15 secs");
                        TimeUnit.SECONDS.sleep(15);
                    } finally {
                        LOGGER.debug("unlocking in worker()");
                        lock.writeLock().unlock();
                    }
                    TimeUnit.MINUTES.sleep(1);
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Exception in worker()", e);
                    throw new ServiceException(e);
                }
                LOGGER.debug("reading country info from file");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void readCountries() {

    }
}
