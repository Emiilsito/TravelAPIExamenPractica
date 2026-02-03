package es.severo.travel_api.controller;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.dto.request.PatchFlightRequest;
import es.severo.travel_api.dto.request.UpdateFlightRequest;
import es.severo.travel_api.service.FlightService;
import jakarta.validation.Valid;
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

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> updateAllFlight(@PathVariable Long id, @RequestBody @Valid UpdateFlightRequest req){
        return ResponseEntity.ok(flightService.updateFlightComplete(id, req));
    }
}
