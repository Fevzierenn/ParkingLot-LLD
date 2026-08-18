package demo.parking.repositories;

import demo.parking.entities.Gate;
import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
    List<Gate> findByTypeAndStatus(GateType type, GateStatus status);
}
