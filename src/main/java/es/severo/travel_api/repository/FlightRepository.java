package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportCodeAndArrivalAirportCode(String arrivalCode, String departureCode);
}
