package es.severo.travel_api.dto.request;

import es.severo.travel_api.domain.BookingStatus;

public record PatchBookingStatusRequest(
        BookingStatus status
) {
}
