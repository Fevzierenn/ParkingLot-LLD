package demo.parking.DTO.ResponseDTO;

import demo.parking.enums.VehicleType;

public record ParkingSpotResponse(
        Long id,
        VehicleType allowedType,
        Integer floorNumber,
        Integer nearness,
        SpotDeviceResponse device
) {
}