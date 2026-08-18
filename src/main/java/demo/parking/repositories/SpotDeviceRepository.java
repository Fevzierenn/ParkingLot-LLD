package demo.parking.repositories;

import demo.parking.entities.SpotDevice;
import demo.parking.enums.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotDeviceRepository extends JpaRepository<SpotDevice, Long> {
    List<SpotDevice> findAllByDeviceStatus(DeviceStatus deviceStatus);
}
