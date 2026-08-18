package demo.parking.services;


import demo.parking.entities.Gate;
import demo.parking.entities.ParkingSpot;
import demo.parking.entities.Ticket;
import demo.parking.entities.Vehicle;
import demo.parking.enums.VehicleType;
import demo.parking.events.TicketGeneratedEventPublisher;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleEntryService {
    Logger logger = LoggerFactory.getLogger(VehicleEntryService.class);
    private final TicketService ticketService;
    private final TicketGeneratedEventPublisher ticketGeneratedEventPublisher;
    private final SpotAllocationService spotAllocationService;
    private final GateService gateService;
    private final VehicleService vehicleService;



    public VehicleEntryService(TicketService ticketService, TicketGeneratedEventPublisher ticketGeneratedEventPublisher, SpotAllocationService spotAllocationService, GateService gateService, VehicleService vehicleService) {
        this.ticketService = ticketService;
        this.ticketGeneratedEventPublisher = ticketGeneratedEventPublisher;
        this.spotAllocationService = spotAllocationService;
        this.gateService = gateService;
        this.vehicleService = vehicleService;
    }


@Transactional
public Ticket parkVehicle(
        @NotNull String plateNo,
        @NotNull VehicleType vehicleType,
        Long gateId
) {
    Vehicle vehicle =
            vehicleService.findOrCreate(plateNo, vehicleType);

    Gate entryGate = gateService.findGateById(gateId);
    return parkVehicle(vehicle, entryGate);
}

    @Transactional
    public Ticket parkVehicle(@NotNull Vehicle vehicle, Gate entryGate) {
        ParkingSpot spot = reserveNearestSpot(vehicle.getType());
        Ticket ticket = ticketService.generateTicket(vehicle, spot, entryGate);
        publishTicketGeneratedEvent(ticket, spot);
        return ticket;
    }

    private ParkingSpot reserveNearestSpot(VehicleType vehicleType) {
        ParkingSpot spot = spotAllocationService.allocateNearestSpot(vehicleType);
        return spot;
    }

    private void publishTicketGeneratedEvent(Ticket ticket, ParkingSpot spot) {
        ticketGeneratedEventPublisher.publishCustomTicketGeneratedPublisher(
                ticket.getVehicle().getUuid(),
                ticket.getUuid(),
                spot.getDevice().getId()
        );
    }
}
