package es.severo.travel_api.dto.request;

import es.severo.travel_api.domain.FlightStatus;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.service.FlightService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatchFlightRequest(
    String flightNumber,
    LocalDate departureDate,
    LocalDateTime departureTime,
    LocalDate arrivalDate,
    LocalDateTime arrivalTime,
    Integer durationMinutes,
    BigDecimal basePrice,
    FlightStatus status,
    Long airlineId,
    Long departureAirportId,
    Long arrivalAirportId
) {
}
