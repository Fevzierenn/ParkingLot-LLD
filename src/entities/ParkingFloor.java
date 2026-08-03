package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class ParkingFloor {

    private long uuid;
    private int floorNumber;
    private List<ParkingSpot> spots;

}
