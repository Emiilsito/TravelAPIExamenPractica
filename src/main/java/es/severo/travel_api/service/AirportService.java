package es.severo.travel_api.service;

import es.severo.travel_api.repository.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

}
