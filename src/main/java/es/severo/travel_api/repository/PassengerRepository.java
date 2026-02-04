package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Passenger;
import es.severo.travel_api.dto.PassengerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByDocumentNumberIgnoreCaseAndIdNot(String documentNumber, Long id);

    Optional<Passenger> findByDocumentNumberIgnoreCase(String documentNumber);

    boolean existsByDocumentNumberContainsIgnoreCaseAndIdNot(String documentNumber, Long id);

}
