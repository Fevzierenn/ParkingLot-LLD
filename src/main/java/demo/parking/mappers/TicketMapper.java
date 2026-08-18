package demo.parking.mappers;

import demo.parking.DTO.ResponseDTO.TicketResponse;
import demo.parking.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = ParkingSpotMapper.class
)
public interface TicketMapper {
    @Mapping(source = "uuid", target = "ticketId")
    @Mapping(source = "vehicle.plateNo", target = "plateNo")
    @Mapping(source = "entryGate.id", target = "entryGateId")
    TicketResponse toResponse(Ticket ticket);

}