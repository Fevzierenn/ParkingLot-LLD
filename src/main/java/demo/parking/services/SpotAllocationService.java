package demo.parking.services;


import demo.parking.Exceptions.ParkingSpotAllocationException;
import demo.parking.entities.ParkingSpot;
import demo.parking.enums.DeviceStatus;
import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import demo.parking.repositories.ParkingSpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SpotAllocationService {
    Logger logger = LoggerFactory.getLogger(SpotAllocationService.class);

    private final ParkingSpotRepository parkingSpotRepository;
    private final SpotDeviceService spotDeviceService;

    public SpotAllocationService(ParkingSpotRepository parkingSpotRepository, SpotDeviceService spotDeviceService) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.spotDeviceService = spotDeviceService;
    }

    @Transactional
    public ParkingSpot allocateNearestSpot(VehicleType vehicleType) {
        //find the available spot for the vehicle closest floor and nearest one.
            ParkingSpot parkingSpot = parkingSpotRepository
                    .findAssignableSpots
                            (vehicleType, SpotStatus.AVAILABLE, DeviceStatus.EMPTY, 1)
                    .orElseThrow(
                            ()-> new ParkingSpotAllocationException("Unable to allocate a parking spot for vehicle type " + vehicleType));

            parkingSpot.setStatus(SpotStatus.RESERVED);

        logger.info(
                "ParkingSpot {} allocated for vehicle type {}",
                parkingSpot.getSpotNumber(), vehicleType);

        return parkingSpot;
    }

    @Transactional
    public void releaseIfAvailable(ParkingSpot assignedSpot) {
        if (assignedSpot.getStatus() == SpotStatus.OCCUPIED) {
            logger.warn(
                    "Spot {} is occupied. Cannot release physical spot.",
                    assignedSpot.getSpotNumber()
            );
            return;
        }
        assignedSpot.setStatus(SpotStatus.AVAILABLE);
        logger.info("Spot {} released. Back to AVAILABLE Mode.", assignedSpot.getSpotNumber());
        spotDeviceService.resetDevice(assignedSpot.getDevice());
    }
}
