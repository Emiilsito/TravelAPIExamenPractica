package es.severo.travel_api.dto;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FlightSearchResultDto(
        Long flightId,
        String flightNumber,
        LocalDate departureDate,
        LocalDateTime departureTime,
        String fromIata,
        String toIata,
        String airlineCode,
        FlightStatus status,
        BigDecimal price
) {
}
