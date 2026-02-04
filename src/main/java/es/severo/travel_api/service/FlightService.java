package es.severo.travel_api.service;

import es.severo.travel_api.domain.Airline;
import es.severo.travel_api.domain.Airport;
import es.severo.travel_api.domain.Flight;
import es.severo.travel_api.domain.FlightStatus;
import es.severo.travel_api.dto.FlightDto;
import es.severo.travel_api.dto.FlightSearchResultDto;
import es.severo.travel_api.dto.request.PatchFlightRequest;
import es.severo.travel_api.dto.request.UpdateFlightRequest;
import es.severo.travel_api.repository.AirlineRepository;
import es.severo.travel_api.repository.AirportRepository;
import es.severo.travel_api.repository.FlightRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;

    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository, AirlineRepository airlineRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
    }

    public FlightDto getFlightById(Long id){
        Flight f = flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo con id " + id + " no encontrado"));
        return toDto(f);
    }

    public List<FlightDto> getFlightByDepartureAndArrivalAirport(String departureCode, String arrivalCode){
        return flightRepository.findByDepartureAirportCodeAndArrivalAirportCode(arrivalCode, departureCode).stream().map(this::toDto).toList();
    }

    public FlightDto update(Long id, PatchFlightRequest req){
        Flight f = flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo no encontrado."));

        Airline airline = airlineRepository.findById(req.airlineId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aerolinea no encontrada."));
        Airport departureAirport = airportRepository.findById(req.departureAirportId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeropuerto de salida no encontrado."));
        Airport arrivalAirport = airportRepository.findById(req.arrivalAirportId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeropuerto de entrada no encontrado"));

        if (flightRepository.existsByFlightNumberAndDepartureDateAndIdNot(f.getFlightNumber(), f.getDepartureDate(), f.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflicto de vuelo");
        }

        f.setFlightNumber(req.flightNumber());
        f.setStatus(req.status());
        f.setDepartureDate(req.departureDate());
        f.setAirline(airline);
        f.setArrivalAirport(arrivalAirport);
        f.setDepartureAirport(departureAirport);
        f.setDepartureTime(req.departureTime());
        f.setArrivalTime(req.arrivalTime());
        f.setArrivalDate(req.arrivalDate());
        f.setBasePrice(req.basePrice());

        Flight saved = flightRepository.save(f);

        return toDto(saved);
    }


    public FlightDto updateFlightComplete(Long id, UpdateFlightRequest req){
        Flight f = flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo no existe"));

        Airline airline = airlineRepository.findById(f.getAirline().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aerolinea no existe"));

        Airport departureAirport = airportRepository.findById(f.getDepartureAirport().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeropuerto de salida no existe"));
        Airport arrivalAirport = airportRepository.findById(f.getArrivalAirport().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aeropuerto de entrada no existe"));

        if (req == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mala request");
        }

        if (flightRepository.existsByFlightNumberAndDepartureDateAndIdNot(f.getFlightNumber(), f.getDepartureDate(), f.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Violacion en la restriccion");
        }

        f.setFlightNumber(req.flightNumber());
        f.setStatus(req.status());
        f.setArrivalTime(req.arrivalTime());
        f.setArrivalDate(req.arrivalDate());
        f.setDepartureDate(req.departureDate());
        f.setDepartureTime(req.departureTime());
        f.setBasePrice(req.basePrice());


        f.setAirline(airline);
        f.setArrivalAirport(arrivalAirport);
        f.setDepartureAirport(departureAirport);

        Flight save = flightRepository.save(f);

        return toDto(save);
    }

    public FlightDto findByStatusAndDepartureDate(FlightStatus status, LocalDate departureDate){
        return flightRepository.findByStatusAndDepartureDate(status, departureDate).map(this::toDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo no encontrado"));
    }

    @Transactional
    public Page<FlightSearchResultDto> searchResults(String from, String to, LocalDate dateFrom, LocalDate dateTo, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable){
        return flightRepository.searchFlights(from, to, dateFrom, dateTo, minPrice, maxPrice, pageable);

    }

    public FlightDto toDto(Flight f){
        return new FlightDto(f.getId(), f.getFlightNumber(), f.getDepartureDate(), f.getDepartureTime(), f.getArrivalDate(), f.getArrivalTime(), f.getDurationMinutes(), f.getBasePrice(), f.getStatus(), f.getAirline().getCode(), f.getDepartureAirport().getCode(), f.getArrivalAirport().getCode());
    }
}
