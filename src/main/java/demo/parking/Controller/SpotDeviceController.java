package demo.parking.Controller;

import demo.parking.DTO.ResponseDTO.SpotDeviceResponse;
import demo.parking.entities.SpotDevice;
import demo.parking.services.ParkingLotService;
import demo.parking.services.SpotDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spot-devices")
public class SpotDeviceController {
    Logger logger = LoggerFactory.getLogger(SpotDeviceController.class);
    private final ParkingLotService parkingLotService;

    public SpotDeviceController( ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }


    @PostMapping("/{deviceId}/vehicle-detection")
    public ResponseEntity vehicleReachedToSpot(
            @PathVariable Long deviceId,
            @RequestParam String plateNo)
    {
        logger.info("VEHICLE CONTROLLER: Vehicle {} reach spot and deviceId:{}  " , plateNo,deviceId);
        SpotDeviceResponse device = parkingLotService.vehicleReachTheSpot(plateNo, deviceId);
        logger.info("VEHICLE CONTROLLER RETURN: Device: "+ device);
        return ResponseEntity.status(HttpStatus.FOUND).body(device);
    }
}
