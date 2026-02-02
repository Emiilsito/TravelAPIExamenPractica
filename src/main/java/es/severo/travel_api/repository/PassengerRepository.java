package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByDocumentNumberIgnoreCaseAndIdNot(String documentNumber);


}
