package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
