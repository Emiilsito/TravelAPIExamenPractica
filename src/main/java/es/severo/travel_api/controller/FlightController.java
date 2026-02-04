package es.severo.travel_api.controller;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.dto.FlightSearchResultDto;
import es.severo.travel_api.dto.GroupBookingsResultDto;
import es.severo.travel_api.dto.request.CreateGroupBookingsRequest;
import es.severo.travel_api.dto.request.PatchFlightRequest;
import es.severo.travel_api.dto.request.UpdateFlightRequest;
import es.severo.travel_api.service.BookingService;
import es.severo.travel_api.service.FlightService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    private final BookingService bookingService;

    public FlightController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> updateAllFlight(@PathVariable Long id, @RequestBody @Valid UpdateFlightRequest req){
        return ResponseEntity.ok(flightService.updateFlightComplete(id, req));
    }

    @GetMapping("/search")
    public ResponseEntity<FlightDto> getFlightByStatusAndDepartureDate(@RequestParam @Valid FlightStatus status, @RequestParam @Valid LocalDate departureDate){
        return ResponseEntity.ok(flightService.findByStatusAndDepartureDate(status, departureDate));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<FlightSearchResultDto>> searchFlights(
            @RequestParam @Pattern(regexp = "^[A-Z]{3}") String from,
            @RequestParam @Pattern(regexp = "^[A-Z]{3}") String to,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo,
            @RequestParam @Positive BigDecimal minPrice,
            @RequestParam @Positive BigDecimal maxPrice,

            Pageable pageable
            ) {
        return ResponseEntity.ok(flightService.searchResults(from, to, dateFrom, dateTo, minPrice, maxPrice, pageable));
    }

    @PostMapping("/{flightId}/bookings/group")
    public ResponseEntity<GroupBookingsResultDto> createGroupBooking(
            @PathVariable Long flightId,
            @Valid @RequestBody CreateGroupBookingsRequest request) {

        GroupBookingsResultDto result = bookingService.createGroupBooking(flightId, request);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/flights/{id}/bookings/group/{ref}")
                .buildAndExpand(flightId, result.groupRef())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

}
