package demo.parking.Exceptions;

public class VehicleHasNonExpiredTicketException extends RuntimeException {
    public VehicleHasNonExpiredTicketException(String message) {
        super(message);
    }
}
