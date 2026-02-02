package es.severo.travel_api.controller;

import es.severo.travel_api.dto.PassengerDto;
import es.severo.travel_api.dto.request.UpdatePassengerRequest;
import es.severo.travel_api.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PutMapping("/{id}")
    private ResponseEntity<PassengerDto> updatePassengerById(@PathVariable long passengerId, @RequestParam UpdatePassengerRequest req){
        return ResponseEntity.ok(passengerService.update(passengerId, req));
    }
}
