package es.severo.travel_api.dto;

import es.severo.travel_api.domain.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FlightDto(
        Long id,
        String flightNumber,
        LocalDate departureDate,
        LocalDateTime departureTime,
        LocalDate arrivalDate,
        LocalDateTime arrivalTime,
        Integer durationMinutes,
        BigDecimal basePrice,
        FlightStatus status,
        String airline,
        String departureAirport,
        String arrivalAirport
) {
}
