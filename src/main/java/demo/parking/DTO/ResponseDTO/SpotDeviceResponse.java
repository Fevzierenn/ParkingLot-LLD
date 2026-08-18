package demo.parking.DTO.ResponseDTO;

import demo.parking.enums.DeviceStatus;

import java.util.UUID;

public record SpotDeviceResponse(
        long id,
        DeviceStatus deviceStatus,
        String message,
        String vehiclePlate,
        String spotNumber) {
}
