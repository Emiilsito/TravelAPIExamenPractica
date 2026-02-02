package es.severo.travel_api.service;

import es.severo.travel_api.domain.Passenger;
import es.severo.travel_api.dto.PassengerDto;
import es.severo.travel_api.dto.request.UpdatePassengerRequest;
import es.severo.travel_api.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerDto update(Long id, UpdatePassengerRequest req){
        Passenger p = passengerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pasajero con id " + id)
        );
        if (passengerRepository.existsByDocumentNumberIgnoreCaseAndIdNot(req.documentNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese documento ya esta en uso");
        }

        p.setDocumentNumber(req.documentNumber());
        p.setGender(req.documentNumber());

        return toDto(p);
    }

    public PassengerDto toDto(Passenger p){
        return new PassengerDto(p.getId(), p.getDocumentNumber(), p.getGender());
    }
}
