package es.severo.travel_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PassengerSeatRequest(
        @NotNull Long passengerId,
        @NotNull @Pattern(regexp = "^[1-9]{1,2}[A-F]") String seatNumber
) {
}
