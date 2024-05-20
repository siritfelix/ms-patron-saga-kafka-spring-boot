package com.msflightsreservations.flightsreservations.domain.service;

import com.msflightsreservations.flightsreservations.infrastructure.mq.dto.BookingRequestDto;

public interface FlightsreservationsService {
    public void processingFlightreservation(BookingRequestDto bookingRequestDto);
}
