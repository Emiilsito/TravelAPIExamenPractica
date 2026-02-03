package es.severo.travel_api.service;

import es.severo.travel_api.domain.Airline;
import es.severo.travel_api.dto.AirlineDto;
import es.severo.travel_api.repository.AirlineRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @Transactional(readOnly = true)
    public List<AirlineDto> getAllSorted(Sort sort){
        return airlineRepository.findAll(sort).stream().map(this::toDto).toList();
    }

    public AirlineDto toDto(Airline airline){
        return new AirlineDto(airline.getId(), airline.getCode(), airline.getName());
    }
}
