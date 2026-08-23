package demo.parking.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidGateTypeException extends RuntimeException {
    public InvalidGateTypeException(String message) {
        super(message);
    }
}
