package es.severo.travel_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateGroupBookingsRequest(
        @NotBlank String groupRef,
        @Email String contactEmail,
        @NotNull @Size(min = 2, max = 6) List<PassengerSeatRequest> passengers
) {
}
