package es.severo.travel_api.service;

import es.severo.travel_api.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

}
