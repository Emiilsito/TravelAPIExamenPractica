package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Booking;
import es.severo.travel_api.domain.BookingStatus;
import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.dto.BookingDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByFlightIdAndSeatSeatNumberAndStatusIs(Long flightId, String seatNumber, BookingStatus status);

    long countByPassengerId(Long passengerId);

    Optional<Booking> findByPassengerIdAndStatusIs(Long passengerId, BookingStatus status);

    BookingDto findFirstByPassengerIdOrderByBookingDateTime(Long passengerId);

    boolean existsByFlightIdAndPassengerId(Long flightId, Long passengerId);

    boolean existsByFlightIdAndSeatSeatNumberAndStatusIn(Long flightId, String seatSeatNumber, List<BookingStatus> statuses);

}
