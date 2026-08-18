package demo.parking.repositories;

import demo.parking.entities.Ticket;
import demo.parking.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Ticket findTicketsByVehicle_Uuid(UUID vehicleUuid);
    
    List<Ticket> findTicketsByVehicle_PlateNoAndStatus(String plateNo, TicketStatus status);

    boolean existsByVehicle_UuidAndStatusNot(UUID uuid, TicketStatus ticketStatus);
}
