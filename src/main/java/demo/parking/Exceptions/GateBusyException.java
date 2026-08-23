package demo.parking.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GateBusyException extends RuntimeException {
    public GateBusyException(String message) {
        super(message);
    }
}
