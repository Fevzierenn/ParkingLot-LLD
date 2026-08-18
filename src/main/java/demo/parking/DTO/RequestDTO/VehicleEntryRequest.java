package demo.parking.DTO.RequestDTO;

import demo.parking.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleEntryRequest (
        @NotBlank(message = "Vehicle Plate No is required.")
        String plateNo,

        @NotNull(message = "Vehicle Type must be declared.")
        VehicleType vehicleType,
        @NotNull(message = "EnterGate ID must be declared.")
        Long gateId
) {

}