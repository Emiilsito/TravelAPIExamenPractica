package es.severo.travel_api.service;

import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    public Flight getFlightById(long id){
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el vuelo con id"));
    }

    public List<Flight> getFlightByDepartureAndArrivalAirport(String departureCode, String arrivalCode){
        return flightRepository.findByDepartureAirportCodeAndArrivalAirportCode(arrivalCode, departureCode);
    }


}
