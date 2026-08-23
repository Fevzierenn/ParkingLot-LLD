package demo.parking.services;

import demo.parking.DTO.ResponseDTO.SpotDeviceResponse;
import demo.parking.entities.*;
import demo.parking.events.TicketGeneratedEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class ParkingLotService {
    private final Logger logger = LoggerFactory.getLogger(ParkingLotService.class);
    private final SpotAllocationService spotAllocationService;
    private final TicketService ticketService;
    private final TicketGeneratedEventPublisher ticketGeneratedEventPublisher;
    private final SpotDeviceService spotDeviceService;
    private final VehicleService vehicleService;

    public ParkingLotService(SpotAllocationService spotAllocationService,
                             TicketService ticketService, TicketGeneratedEventPublisher ticketGeneratedEventPublisher,
                             SpotDeviceService spotDeviceService, VehicleService vehicleService) {
        this.spotAllocationService = spotAllocationService;
        this.ticketService = ticketService;
        this.ticketGeneratedEventPublisher = ticketGeneratedEventPublisher;
        this.spotDeviceService = spotDeviceService;
        this.vehicleService = vehicleService;
    }



    @Transactional
    public SpotDeviceResponse vehicleReachTheSpot(String plateNo, Long deviceId) {
        logger.info("Vehicle {} reached spot and deviceId:{}", plateNo, deviceId);

        Vehicle vehicle = vehicleService.findVehicleByPlateNo(plateNo);
        SpotDevice device = spotDeviceService.findSpotDeviceById(deviceId);
        Ticket ticket = ticketService.findTicketByVehiclePlateNo(vehicle.getPlateNo());

        ParkingSpot assignedSpot = ticket.getAssignedSpot();
        ParkingSpot actualSpot = device.getSpot();

        if (isDifferentSpot(assignedSpot, actualSpot)) {
            logger.warn("Vehicle parked in a different spot. Assigned spot:{} and actual spot:{}",assignedSpot,actualSpot);
            spotAllocationService.releaseIfAvailable(assignedSpot);
        }
        spotDeviceService.occupy(device, plateNo);
        ticketService.markAsParked(ticket, vehicle, actualSpot);
        logger.info("Vehicle parked successfully. Device:{} Ticket:{}", device, ticket);

        return createSpotDeviceResponse(device, vehicle);
    }


    private boolean isDifferentSpot(ParkingSpot assignedSpot, ParkingSpot actualSpot) {
        return !Objects.equals(
                assignedSpot.getId(),
                actualSpot.getId()
        );
    }

    //it will handling using mappers struct. for now can stay like this.
    private SpotDeviceResponse createSpotDeviceResponse(SpotDevice device, Vehicle vehicle) {
        return new SpotDeviceResponse(
                device.getId(),
                device.getDeviceStatus(),
                device.getMessage(),
                vehicle.getPlateNo(),
                device.getSpot().getSpotNumber()
        );
    }
}
