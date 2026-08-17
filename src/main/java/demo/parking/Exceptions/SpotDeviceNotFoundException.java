package demo.parking.Exceptions;

public class SpotDeviceNotFoundException extends RuntimeException {
    public SpotDeviceNotFoundException(String message) {
        super(message);
    }
}
