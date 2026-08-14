package demo.parking.repositories;

import demo.parking.entities.ParkingSpot;
import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    ParkingSpot findFirstByAllowedTypeAndStatusOrderByFloorAscNearnessAsc(VehicleType allowedType, SpotStatus status);
}
