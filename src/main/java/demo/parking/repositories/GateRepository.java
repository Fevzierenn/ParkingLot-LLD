package demo.parking.repositories;

import demo.parking.entities.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {
}
