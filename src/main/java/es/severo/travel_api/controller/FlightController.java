package es.severo.travel_api.controller;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.dto.request.PatchFlightRequest;
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
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FlightDto> updateFlightById(@PathVariable Long id, PatchFlightRequest req){
        return ResponseEntity.ok(flightService.update(id, req));
    }
}
