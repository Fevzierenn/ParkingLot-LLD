package demo.parking.Exceptions;

public class SpotDeviceNotWorkingException extends RuntimeException {
    public SpotDeviceNotWorkingException(String message) {
        super(message);
    }
}
