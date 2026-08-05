package demo.parking.enums;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR(1),
    MOTORCYCLE(0.5),
    TRUCK(2.5),
    VAN(5);

    private final double code;

    VehicleType(double code){
        this.code = code;
    }

}
