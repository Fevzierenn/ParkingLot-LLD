package demo.parking.entities;

import demo.parking.enums.DeviceStatus;
import demo.parking.enums.SpotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.Duration;
import java.util.UUID;
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class SpotDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @OneToOne
    private ParkingSpot spot;
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    private String message;
    private String vehiclePlate;
    private  Duration maxWaitingTime;

    @Override
    public String toString() {
        return "SpotDevice{" +
                "uuid=" + uuid +
                ", spot number=" + spot.getSpotNumber() +
                ", deviceStatus=" + deviceStatus +
                ", message='" + message + '\'' +
                ", vehiclePlate='" + vehiclePlate + '\'' +
                ", maxWaitingTime=" + maxWaitingTime +
                '}';
    }
}
