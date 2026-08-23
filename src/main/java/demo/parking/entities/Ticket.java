package demo.parking.entities;


import demo.parking.enums.PricingPolicy;
import demo.parking.enums.TicketStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotNull
    private LocalDateTime entryTime;

    private LocalDateTime actualParkedTime;

    private LocalDateTime exitTime;

    @ManyToOne
    @JoinColumn
    private Gate entryGate;

    @ManyToOne
    @JoinColumn
    private Gate exitGate;

    @ManyToOne
    private ParkingSpot assignedSpot;

    @ManyToOne
    private ParkingSpot actualSpot;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "plateNo")
    private Vehicle vehicle;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private PricingPolicy pricingPolicy;
    private String pricingDescription;
    private boolean penaltyApplied;
    private String penaltyReason;

    @Override
    public String toString() {
        return "Ticket{" +
                "uuid=" + uuid +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", entryGate=" + entryGate +
                ", exitGate=" + exitGate +
                ", assignedSpot=" + assignedSpot +
                ", actualSpot=" + actualSpot +
                ", status=" + status +
                ", vehicle=" + vehicle +
                ", pricingPolicy=" + pricingPolicy +
                ", pricingDescription='" + pricingDescription + '\'' +
                ", penaltyApplied=" + penaltyApplied +
                ", penaltyReason='" + penaltyReason + '\'' +
                '}';
    }
}
