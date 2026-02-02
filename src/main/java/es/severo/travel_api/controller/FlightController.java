package es.severo.travel_api.controller;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Flight>> getFlightByDepartureAndArrival(
            @RequestParam(required = false)
            String departureCode,
            @RequestParam(required = true)
            String arrivalCode
    ){
        return ResponseEntity.ok(flightService.getFlightByDepartureAndArrivalAirport(arrivalCode, departureCode));
    }
}
