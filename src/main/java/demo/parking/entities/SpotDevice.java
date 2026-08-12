package demo.parking.entities;

import demo.parking.enums.SpotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import java.time.Duration;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "device")
public class SpotDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @OneToOne(mappedBy = "device")
    private ParkingSpot spot;
    @Enumerated(EnumType.STRING)
    private SpotStatus spotStatus;

    private String message;
    private String vehiclePlate;
    @Value("${spot.allocation.time}")
    private  Duration maxWaitingTime;
}
