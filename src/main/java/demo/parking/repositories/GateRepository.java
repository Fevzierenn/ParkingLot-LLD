package demo.parking.repositories;

import demo.parking.entities.Gate;
import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {

    List<Gate> findByTypeAndStatus(GateType type, GateStatus status);

    Optional<Gate> findFirstByTypeAndStatus(GateType type, GateStatus status);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gate g WHERE g.id = :gateId")
    Optional<Gate> findByIdForUpdate(Long gateId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Gate g
           SET g.status = :closedStatus,
               g.openedAt = null
         WHERE g.status = :openStatus
           AND (g.openedAt IS NULL OR g.openedAt < :threshold)
    """)
    int closeExpiredOpenGates(GateStatus openStatus,
                              GateStatus closedStatus,
                              LocalDateTime threshold);
}
