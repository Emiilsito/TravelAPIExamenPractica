package es.severo.travel_api.controller;

import es.severo.travel_api.dto.BookingDto;
import es.severo.travel_api.dto.GroupBookingsResultDto;
import es.severo.travel_api.dto.request.CreateGroupBookingsRequest;
import es.severo.travel_api.dto.request.PatchBookingStatusRequest;
import es.severo.travel_api.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingDto> updateStatus(@PathVariable Long id, @RequestBody @Valid PatchBookingStatusRequest req){
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, req));
    }

    @GetMapping("/page")
    public Page<BookingDto> getAllBookingPage(@PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        if (pageable.getPageSize() > 30){
            pageable = PageRequest.of(pageable.getPageNumber(), 30, pageable.getSort());
        }

        return bookingService.getAll(pageable);
    }
}
