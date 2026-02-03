package es.severo.travel_api.controller;

import es.severo.travel_api.dto.PassengerDto;
import es.severo.travel_api.dto.request.UpdatePassengerRequest;
import es.severo.travel_api.service.PassengerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/page")
    private Page<PassengerDto> getAll(@PageableDefault(page = 0, size = 8, sort = "documentNumber", direction = Sort.Direction.ASC) Pageable pageable) {
        return passengerService.getAll(pageable);
    }
}
