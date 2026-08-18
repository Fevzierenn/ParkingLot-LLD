package demo.parking.services;

import demo.parking.entities.Vehicle;
import demo.parking.enums.VehicleType;
import demo.parking.repositories.VehicleRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {
    Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle save(@NotNull Vehicle vehicle) {
        log.info("VehicleService.save(): Saving vehicle {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle findOrCreate(
            @NotEmpty String plateNo,
            @NotNull VehicleType vehicleType
    ) {
        return vehicleRepository.findByPlateNo(plateNo)
                .orElseGet(() -> {
                    Vehicle vehicle = Vehicle.builder()
                            .plateNo(plateNo)
                            .type(vehicleType)
                            .build();
                    log.info("VehicleService.save(): Saving vehicle {}", vehicle);
                    return vehicleRepository.save(vehicle);
                });
    }

    public Vehicle findVehicleByPlateNo(String plateNo) {
        return vehicleRepository.findByPlateNo(plateNo).
                orElseThrow(() -> new RuntimeException("Vehicle not found with plateNo: " + plateNo));
    }
}
