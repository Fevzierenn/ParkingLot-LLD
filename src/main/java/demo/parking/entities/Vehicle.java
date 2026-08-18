package demo.parking.entities;

import demo.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String plateNo;
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Override
    public String toString() {
        return "Vehicle{" +
                "uuid=" + uuid +
                ", plateNo='" + plateNo + '\'' +
                ", type=" + type +
                '}';
    }
}
