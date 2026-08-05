package demo.parking.entities;

import demo.parking.entities.ParkingFloor;

import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class ParkingSpot {

    private long uuid;
    private ParkingFloor floor;
    private int nearness;       // measure of floor entrance.
    private String spotNumber;
    private VehicleType allowedType;
    private SpotStatus status;
    private SpotDevice device;
}
