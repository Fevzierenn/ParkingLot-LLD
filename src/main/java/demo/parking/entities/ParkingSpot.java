package demo.parking.entities;

import demo.parking.entities.ParkingFloor;

import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "spot")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @ManyToOne
    @JoinColumn
    private ParkingFloor floor;
    private int nearness;       // measure of floor entrance.
    private String spotNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType allowedType;
    @Enumerated(EnumType.STRING)
    private SpotStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private SpotDevice device;
}
