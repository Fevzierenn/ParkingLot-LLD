package demo.parking.entities;

import demo.parking.enums.DeviceStatus;
import demo.parking.enums.SpotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class SpotDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "device")
    private ParkingSpot spot;

    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    @Version
    private Long version;

    private String message;
    private String vehiclePlate;

    private LocalDateTime waitingStartedAt;
    private LocalDateTime waitingDeadline;

    @Override
    public String toString() {
        return "SpotDevice{" +
                "uuid=" + id +
                ", spot number=" + spot.getSpotNumber() +
                ", deviceStatus=" + deviceStatus +
                ", message='" + message + '\'' +
                ", vehiclePlate='" + vehiclePlate + '\'' +
                ", waitingStart=" + waitingStartedAt +
                ", waitingDeadline=" + waitingDeadline +
                '}';
    }
}
