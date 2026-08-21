package demo.parking.config.seedData;

import demo.parking.common.Address;
import demo.parking.entities.Gate;
import demo.parking.entities.ParkingFloor;
import demo.parking.entities.ParkingLot;
import demo.parking.entities.ParkingSpot;
import demo.parking.entities.SpotDevice;
import demo.parking.enums.*;
import demo.parking.repositories.GateRepository;
import demo.parking.repositories.ParkingLotRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ParkingLotDataInitializer {

    @Bean
    CommandLineRunner seedParkingLot(
            ParkingLotRepository parkingLotRepository,
            GateRepository gateRepository,
            @Value("${spot.allocation.time:5m}") Duration spotAllocationTime
    ) {
        return args -> {
            if (parkingLotRepository.count() > 0) {
                return;
            }

            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setName("ParkingLot HANCAGİZ");
            parkingLot.setAddress(new Address("HATAY", "DEFNE", "Hancagiz", "31160"));

            List<ParkingFloor> floors = new ArrayList<>();
            floors.add(createFloor(parkingLot, 0, spotAllocationTime,
                    spotPlan(VehicleType.VAN, 20),
                    spotPlan(VehicleType.TRUCK, 5),
                    spotPlan(VehicleType.MOTORCYCLE, 40),
                    spotPlan(VehicleType.CAR, 30)));
            floors.add(createFloor(parkingLot, 1, spotAllocationTime,
                    spotPlan(VehicleType.MOTORCYCLE, 20),
                    spotPlan(VehicleType.CAR, 80)));
            floors.add(createFloor(parkingLot, 2, spotAllocationTime,
                    spotPlan(VehicleType.CAR, 100)));
            floors.add(createFloor(parkingLot, 3, spotAllocationTime,
                    spotPlan(VehicleType.CAR, 120)));

            parkingLot.setFloors(floors);
            parkingLotRepository.save(parkingLot);

            seedGates(gateRepository);
        };
    }

    private ParkingFloor createFloor(
            ParkingLot parkingLot,
            int floorNumber,
            Duration spotAllocationTime,
            SpotPlan... spotPlans
    ) {
        ParkingFloor floor = new ParkingFloor();
        floor.setFloorNumber(floorNumber);

        List<ParkingSpot> spots = new ArrayList<>();
        int nearness = 1;

        for (SpotPlan spotPlan : spotPlans) {
            for (int i = 1; i <= spotPlan.count(); i++) {
                ParkingSpot spot = new ParkingSpot();
                spot.setFloor(floor);
                spot.setAllowedType(spotPlan.vehicleType());
                spot.setStatus(SpotStatus.AVAILABLE);
                spot.setNearness(nearness++);
                spot.setSpotNumber(formatSpotNumber(floorNumber, spotPlan.vehicleType(), i));

                SpotDevice device = new SpotDevice();
                device.setDeviceStatus(DeviceStatus.EMPTY);
                device.setMessage("Ready");
                device.setVehiclePlate(null);

                spot.setDevice(device);
                device.setSpot(spot);
                spots.add(spot);
            }
        }

        floor.setSpots(spots);
        return floor;
    }

    private String formatSpotNumber(int floorNumber, VehicleType vehicleType, int sequence) {
        return "F" + floorNumber + "-" + vehicleType.name() + "-" + String.format("%03d", sequence);
    }

    private SpotPlan spotPlan(VehicleType vehicleType, int count) {
        return new SpotPlan(vehicleType, count);
    }

    private void seedGates(GateRepository gateRepository) {
        gateRepository.save(createGate(GateType.ENTRY));
        gateRepository.save(createGate(GateType.ENTRY));
        gateRepository.save(createGate(GateType.EXIT));
        gateRepository.save(createGate(GateType.EXIT));
    }

    private Gate createGate(GateType gateType) {
        Gate gate = new Gate();
        gate.setType(gateType);
        gate.setStatus(GateStatus.CLOSED);
        return gate;
    }

    private record SpotPlan(VehicleType vehicleType, int count) {
    }
}
