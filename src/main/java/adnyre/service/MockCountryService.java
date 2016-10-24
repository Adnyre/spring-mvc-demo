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

    private static List<Country> COUNTRIES;

    private static final Logger LOGGER = Logger.getLogger(MockCountryService.class);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean checkIfCodeExists(String code) {
        lock.readLock().lock();
        try {
            return COUNTRIES.stream().map(Country::getAlpha3Code).anyMatch(x -> x.equalsIgnoreCase(code));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        lock.readLock().lock();
        try {
            Optional<Country> opt = COUNTRIES.stream().filter(x -> x.getAlpha3Code().equalsIgnoreCase(code)).findFirst();
            if (opt.isPresent()) {
                return opt.get();
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public List<String> getAllCodes() {
        lock.readLock().lock();
        try {
            return COUNTRIES.stream().map(Country::getAlpha3Code).collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @PostConstruct
    private void worker() {
        LOGGER.debug("worker method invoked");
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("countries")) {
                lock.writeLock().lock();
                try {
                    COUNTRIES = mapper.readValue(in, new TypeReference<List<Country>>() {
                    });
                } finally {
                    lock.writeLock().unlock();
                }
            } catch (IOException e) {
                LOGGER.error("Exception in worker()", e);
                throw new ServiceException(e);
            }
            LOGGER.debug("reading country info from file for the 1st time");
            latch.countDown();
            while (true) {
                try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("countries")) {
                    TimeUnit.MINUTES.sleep(1);
                    lock.writeLock().lock();
                    try {
                        COUNTRIES = mapper.readValue(in, new TypeReference<List<Country>>() {
                        });
                    } finally {
                        lock.writeLock().unlock();
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Exception in worker()", e);
                    throw new ServiceException(e);
                }
                LOGGER.debug("reading country info from file");
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Exception in worker()", e);
            throw new ServiceException(e);
        }
    }
}
