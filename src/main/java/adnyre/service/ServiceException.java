package adnyre.service;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="There was an error processing your request")
public class ServiceException extends RuntimeException {

    private static final Logger LOGGER = Logger.getLogger(ServiceException.class);

    private static final void log() {
        LOGGER.error("Service error thrown");
    }

    public ServiceException(Throwable cause) {
        super(cause);
        log();
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        log();
    }
}
