package entities;

import common.Address;
import enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class ParkingLot{

    private long uuid;
    private String name;
    private Address address;
    private List<ParkingFloor> floors;

    public ParkingSpot findAvailableSpot(VehicleType type){
        return new ParkingSpot();
    }

}