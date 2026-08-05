package entities;


import enums.PricingPolicy;
import enums.TicketStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Data
public class Ticket {
    private UUID uuid;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Gate entryGate;
    private Gate exitGate;
    private ParkingSpot spot;
    private TicketStatus status;
    private Vehicle vehicle;
    private PricingPolicy pricingPolicy;
    private String pricingDescription;



}
