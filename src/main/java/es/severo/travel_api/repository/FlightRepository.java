package es.severo.travel_api.repository;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;
import es.severo.travel_api.dto.AirlineFlightCountDto;
import es.severo.travel_api.dto.FlightBookingCountDto;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.dto.FlightSearchResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportCodeAndArrivalAirportCode(String arrivalCode, String departureCode);

    boolean existsByFlightNumberAndDepartureDateAndIdNot(String flightNumber, LocalDate departureDate, Long id);

    Optional<Flight> findByStatusAndDepartureDate(FlightStatus status, LocalDate departureDate);

    Optional<Flight> findByDepartureAirportCode(String departureAirportCode);

    Optional<Flight> findByAirlineCodeOrderByDepartureDateAscDepartureTimeAsc(String airlineCode);

    Optional<Flight> findTop5ByDepartureAirportCodeAndArrivalAirportCodeAndDepartureDateOrderByBasePriceAsc(String departureAirport_code, String arrivalAirport_code, LocalDate departureDate);

    Long countByAirlineCode(String airlineCode);

    @Query("""
SELECT new es.severo.travel_api.dto.AirlineFlightCountDto(f.airline.code, f.airline.name, COUNT(f))
FROM Flight f
GROUP BY f.airline.code, f.airline.name
""")
    List<AirlineFlightCountDto> findStatisticsByAirline();

    @Query("""
SELECT new es.severo.travel_api.dto.FlightBookingCountDto(f.flightNumber, f.departureDate, COUNT(f.bookings))
FROM Flight f
WHERE f.departureDate = :date
GROUP BY f.flightNumber, f.departureDate
""")
    List<FlightBookingCountDto> findBookingsByFlight(@Param("date") LocalDate date);

    @Query("""
SELECT new es.severo.travel_api.dto.FlightSearchResultDto(f.id, f.flightNumber, f.departureDate, f.departureTime, f.departureAirport.code, f.arrivalAirport.code, f.airline.code, f.status, f.basePrice)
FROM Flight f
WHERE :from IS NULL OR f.departureAirport.code = :from
AND :to IS NULL OR f.arrivalAirport.code = :to
AND :dateFrom IS NULL OR f.departureDate = :dateFrom
AND :dateTo IS NULL OR f.arrivalDate = :dateTo
AND f.basePrice BETWEEN :minPrice AND :maxPrice
""")
    Page<FlightSearchResultDto> searchFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

}
