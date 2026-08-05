package demo.parking.entities;

import demo.parking.enums.SpotStatus;

import java.time.Clock;
import java.time.Duration;
import java.util.UUID;

public class SpotDevice {

    private long uuid;
    private ParkingSpot spot;
    private SpotStatus spotStatus;
    private String message;
    private String vehiclePlate;
    private final Duration maxWaitingTime = Duration.ofMinutes(5);
}
