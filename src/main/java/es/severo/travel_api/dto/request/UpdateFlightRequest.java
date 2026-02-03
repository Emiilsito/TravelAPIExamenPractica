package es.severo.travel_api.dto.request;

import es.severo.travel_api.domain.FlightStatus;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateFlightRequest(
        String flightNumber,
        LocalDate departureDate,
        LocalDateTime departureTime,
        LocalDate arrivalDate,
        LocalDateTime arrivalTime,
        Integer durationMinutes,
        BigDecimal basePrice,
        FlightStatus status,
        Long airlineId,
        Long departureId,
        Long arrivalId
) {
}
