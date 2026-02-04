package es.severo.travel_api.service;

import es.severo.travel_api.domain.*;
import es.severo.travel_api.dto.BookingDto;
import es.severo.travel_api.dto.GroupBookingsResultDto;
import es.severo.travel_api.dto.request.CreateGroupBookingsRequest;
import es.severo.travel_api.dto.request.PassengerSeatRequest;
import es.severo.travel_api.dto.request.PatchBookingStatusRequest;
import es.severo.travel_api.repository.BookingRepository;
import es.severo.travel_api.repository.FlightRepository;
import es.severo.travel_api.repository.PassengerRepository;
import es.severo.travel_api.repository.SeatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository, PassengerRepository passengerRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.seatRepository = seatRepository;
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

    @Transactional
    public GroupBookingsResultDto createGroupBooking(Long flightId, CreateGroupBookingsRequest req){
        Flight f = flightRepository.findById(flightId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vuelo con esa id no encontrado"));

        if (f.getStatus() == FlightStatus.CANCELLED){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vuelo no permite reservas");
        }

        List<BookingDto> savedBookings = new ArrayList<>();

        for (PassengerSeatRequest pRequest: req.passengers()){
            Passenger p = passengerRepository.findById(pRequest.passengerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasajero con esa id no encontrado"));

            Seat s = seatRepository.findByFlightIdAndSeatNumber(flightId, pRequest.seatNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiento con esa id no encontrado"));

            if (!seatRepository.existsByFlightIdAndSeatNumber(flightId, pRequest.seatNumber())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El asiento no existe");
            }

            if (bookingRepository.existsByFlightIdAndPassengerId(flightId, pRequest.passengerId())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El pasajero ya tiene reservado");
            }

            if (bookingRepository.existsByFlightIdAndSeatSeatNumberAndStatusIn(
                    flightId, pRequest.seatNumber(), List.of(BookingStatus.CONFIRMED, BookingStatus.CREATED)
            )){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El asiento ya esta ocupado");
            }

            String locator = req.groupRef() + "-" + p.getId() + "-" + pRequest.seatNumber();

            Booking b = new Booking();
            b.setStatus(BookingStatus.CONFIRMED);
            b.setFlight(f);
            b.setBookingDateTime(LocalDateTime.now());
            b.setPassenger(p);
            b.setLocator(locator);
            b.setPricePaid(f.getBasePrice().add(new BigDecimal("100")));
            b.setSeat(s);

            Booking saved = bookingRepository.save(b);
            savedBookings.add(toDto(saved));
        }
        return new GroupBookingsResultDto(flightId, req.groupRef(), req.contactEmail(), savedBookings);
    }
    public BookingDto toDto(Booking b){
        return new BookingDto(b.getId(), b.getLocator(), b.getBookingDateTime(), b.getPricePaid(), b.getStatus(), b.getPassenger().getId(), b.getFlight().getId(), b.getSeat().getId());
    }

}
