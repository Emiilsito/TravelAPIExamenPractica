package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;
import es.severo.travel_api.dto.FlightDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<FlightDto> findByDepartureAirportCodeAndArrivalAirportCode(String arrivalCode, String departureCode);

    boolean existsByFlightNumberAndDepartureDateAndIdNot(String flightNumber, LocalDate departureDate, Long id);
}
