package demo.parking.services;

import demo.parking.Exceptions.VehicleHasNonExpiredTicketException;
import demo.parking.entities.Gate;
import demo.parking.entities.ParkingSpot;
import demo.parking.entities.Ticket;
import demo.parking.entities.Vehicle;
import demo.parking.enums.TicketStatus;
import demo.parking.Exceptions.TicketNotFoundException;
import demo.parking.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service

public class TicketService {
    Logger logger = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final PricingService pricingService;
    public TicketService(TicketRepository ticketRepository, PricingService pricingService) {
        this.ticketRepository = ticketRepository;
        this.pricingService = pricingService;
    }


    private boolean hasNotExpiredTicket(Vehicle vehicle) {
        return ticketRepository.existsByVehicle_UuidAndStatusNot(vehicle.getUuid(), TicketStatus.EXPIRED);
    }

    public Ticket generateTicket(Vehicle vehicle, ParkingSpot assignedSpot, Gate entryGate) {
        if (hasNotExpiredTicket(vehicle))
            throw new VehicleHasNonExpiredTicketException("Vehicle already has a non-expired ticket.");


        Ticket ticket = Ticket.builder()
                .assignedSpot(assignedSpot)
                .vehicle(vehicle)
                .entryGate(entryGate)
                .entryTime(LocalDateTime.now())
                .pricingPolicy(pricingService.getPricingPolicy())
                .pricingDescription(pricingService.getPricingDescription())
                .status(TicketStatus.ACTIVE)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("Vehicle ticket generated and ticket: " + ticket);
        if(savedTicket == null) throw new RuntimeException("Ticket could not be saved");
        return savedTicket;
    }

    public Ticket findTicketByTicketId(UUID uuid){
        return ticketRepository.findById(uuid).orElseThrow(
                ()-> new TicketNotFoundException("Ticket could not be found with id: " + uuid)
        );
    }

    public Ticket findTicketByVehiclePlateNo(String plateNo){
      List<Ticket> tickets = ticketRepository.findTicketsByVehicle_PlateNoAndStatus(plateNo,TicketStatus.ACTIVE);
      if(tickets.isEmpty()) throw new TicketNotFoundException("Ticket could not be found for plateNo: " + plateNo);
      if(tickets.size() > 1) throw new RuntimeException("Too Many ACTIVE Tickets in the SYSTEM for plate:" + plateNo);
      Ticket activeTicket = tickets.getFirst();
      logger.info("Vehicle ticket found: " + activeTicket);
      return activeTicket;
    }

    public void markAsParked(
            Ticket ticket,
            Vehicle vehicle,
            ParkingSpot actualSpot
    ) {
        ticket.setVehicle(vehicle);
        ticket.setActualSpot(actualSpot);
        ticket.setStatus(TicketStatus.PARKED);
        ticket.setActualParkedTime(LocalDateTime.now());
    }
}
