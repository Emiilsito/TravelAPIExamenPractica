package es.severo.travel_api.dto;

import java.time.LocalDate;

public record FlightBookingCountDto(
        String flightNumber,
        LocalDate departureDate,
        Long totalBookings
) {
}
