package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Airline;
import es.severo.travel_api.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
