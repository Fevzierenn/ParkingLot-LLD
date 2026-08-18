package demo.parking.entities;

import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "spot")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private ParkingFloor floor;
    private int nearness;       // measure of floor entrance.
    private String spotNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType allowedType;
    @Enumerated(EnumType.STRING)
    private SpotStatus status;

    @OneToOne(cascade = CascadeType.ALL)   // or CascadeType.ALL
    @JoinColumn(name = "device_id")
    private SpotDevice device;

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "uuid=" + id +
                ", floor=" + floor.getFloorNumber() +
                ", nearness=" + nearness +
                ", spotNumber='" + spotNumber + '\'' +
                ", allowedType=" + allowedType +
                ", status=" + status +
                ", device=" + device+
                '}';
    }
}
