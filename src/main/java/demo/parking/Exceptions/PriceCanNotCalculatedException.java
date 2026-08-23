package demo.parking.Exceptions;

public class PriceCanNotCalculatedException extends RuntimeException {
    public PriceCanNotCalculatedException(String message) {
        super(message);
    }
}
