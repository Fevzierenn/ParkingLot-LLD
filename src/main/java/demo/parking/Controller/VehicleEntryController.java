package demo.parking.Controller;


import demo.parking.DTO.RequestDTO.VehicleEntryRequest;
import demo.parking.DTO.ResponseDTO.TicketResponse;
import demo.parking.entities.Gate;
import demo.parking.entities.Ticket;
import demo.parking.entities.Vehicle;
import demo.parking.mappers.TicketMapper;
import demo.parking.services.VehicleEntryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle-entry")
public class VehicleEntryController {
    Logger logger = LoggerFactory.getLogger(VehicleEntryController.class);
    private final VehicleEntryService vehicleEntryService;
    private final TicketMapper ticketMapper;

    public VehicleEntryController(VehicleEntryService vehicleEntryService, TicketMapper ticketMapper) {
        this.vehicleEntryService = vehicleEntryService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> vehicleEntry(
            @RequestBody VehicleEntryRequest request) {

        logger.info("VEHICLE REACHED TO GATE AND ENTRY OPERATION STARTS");

        Ticket ticket = vehicleEntryService.parkVehicle(
                request.plateNo(),
                request.vehicleType(),
                request.gateId()
        );

        return ResponseEntity.ok(ticketMapper.toResponse(ticket));
    }
}