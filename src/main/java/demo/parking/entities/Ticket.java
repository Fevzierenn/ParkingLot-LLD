package demo.parking.entities;


import demo.parking.enums.PricingPolicy;
import demo.parking.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private LocalDateTime entryTime;
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
    @JoinColumn
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private PricingPolicy pricingPolicy;
    private String pricingDescription;
    private boolean penaltyApplied;
    private String penaltyReason;



}
