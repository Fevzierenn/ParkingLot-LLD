package demo.parking.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GateNotAvailableException extends RuntimeException {
    public GateNotAvailableException(String message) {
        super(message);
    }
}
