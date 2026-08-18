package demo.parking.DTO.ResponseDTO;

import demo.parking.entities.ParkingSpot;
import demo.parking.enums.PricingPolicy;

import java.util.UUID;

public record TicketResponse(
        UUID ticketId,
        String plateNo,
        ParkingSpotResponse assignedSpot,
        Long entryGateId,
        PricingPolicy pricingPolicy,
        String pricingDescription

) {
}
