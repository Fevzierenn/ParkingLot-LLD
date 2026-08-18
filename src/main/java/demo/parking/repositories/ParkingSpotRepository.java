package demo.parking.repositories;

import demo.parking.entities.ParkingSpot;
import demo.parking.enums.DeviceStatus;
import demo.parking.enums.SpotStatus;
import demo.parking.enums.VehicleType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({
//            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "2000")
//    })
//    Optional<ParkingSpot> findFirstByAllowedTypeAndStatusOrderByFloorAscNearnessAsc(VehicleType allowedType, SpotStatus status);
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("""
    SELECT ps
    FROM spot ps
    JOIN ps.device d
    WHERE ps.allowedType = :vehicleType
      AND ps.status = :spotStatus
      AND d.deviceStatus = :deviceStatus
    ORDER BY ps.floor.floorNumber ASC, ps.nearness ASC LIMIT :limit
""")
Optional<ParkingSpot> findAssignableSpots(
        VehicleType vehicleType,
        SpotStatus spotStatus,
        DeviceStatus deviceStatus,
        int limit
);
}
