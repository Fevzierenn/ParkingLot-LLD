package demo.parking.repositories;

import demo.parking.entities.SpotDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotDeviceRepository extends JpaRepository<SpotDevice, Long> {
}
