package demo.parking.services;

import demo.parking.entities.Vehicle;
import demo.parking.repositories.VehicleRepository;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle save(@NotEmpty Vehicle vehicle) {
        log.info("VehicleService.save(): Saving vehicle ", vehicle);
        return vehicleRepository.findByPlateNo(vehicle.getPlateNo())
                    .orElseGet(() -> vehicleRepository.save(vehicle));
        }
}
