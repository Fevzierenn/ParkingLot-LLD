package demo.parking.Exceptions;

import java.time.Clock;
import java.time.LocalDateTime;

public record ErrorResponse (
        String message,
        LocalDateTime time
){

}
