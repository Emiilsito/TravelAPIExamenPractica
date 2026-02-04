package es.severo.travel_api.dto;

public record AirlineFlightCountDto(
        String airlineCode,
        String airlineName,
        Long totalFlights
) {
}
