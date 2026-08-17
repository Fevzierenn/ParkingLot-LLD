package demo.parking.Exceptions;

public class ParkingSpotAllocationException extends RuntimeException {
    public ParkingSpotAllocationException(String message) {
        super(message);
    }
}
