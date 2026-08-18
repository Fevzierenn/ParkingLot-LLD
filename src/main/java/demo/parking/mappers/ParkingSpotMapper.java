package demo.parking.mappers;

import demo.parking.DTO.ResponseDTO.ParkingSpotResponse;
import demo.parking.entities.ParkingSpot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingSpotMapper {

    @Mapping(source = "floor.floorNumber", target = "floorNumber")
    ParkingSpotResponse toResponse(ParkingSpot parkingSpot);
}