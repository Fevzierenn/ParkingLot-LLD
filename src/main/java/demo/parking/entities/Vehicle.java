package demo.parking.entities;

import demo.parking.enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

    private UUID uuid;
    private String plateNo;
    private VehicleType type;
}
