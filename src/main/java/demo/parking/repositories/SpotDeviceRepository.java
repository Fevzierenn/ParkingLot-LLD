package demo.parking.repositories;

import demo.parking.entities.SpotDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotDeviceRepository extends JpaRepository<SpotDevice, Long> {
}
