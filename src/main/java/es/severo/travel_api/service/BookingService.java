package es.severo.travel_api.service;

import es.severo.travel_api.domain.Booking;
import es.severo.travel_api.dto.BookingDto;
import es.severo.travel_api.dto.request.PatchBookingStatusRequest;
import es.severo.travel_api.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingDto updateBookingStatus(Long id, PatchBookingStatusRequest req){
        Booking b = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        if (req == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflicto con la reserva");
        }

        b.setStatus(req.status());

        Booking saved = bookingRepository.save(b);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<BookingDto> getAll(Pageable pageable){
        return bookingRepository.findAll(pageable).map(this::toDto);
    }

    public BookingDto toDto(Booking b){
        return new BookingDto(b.getId(), b.getLocator(), b.getBookingDateTime(), b.getPricePaid(), b.getStatus(), b.getPassenger().getId(), b.getFlight().getId(), b.getSeat().getId());
    }

}
