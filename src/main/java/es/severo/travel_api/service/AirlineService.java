package es.severo.travel_api.service;

import es.severo.travel_api.repository.AirlineRepository;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

}
