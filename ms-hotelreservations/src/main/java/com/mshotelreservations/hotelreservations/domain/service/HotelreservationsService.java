package com.mshotelreservations.hotelreservations.domain.service;

import com.mshotelreservations.hotelreservations.infrastructure.mq.dto.BookingRequestDto;

public interface HotelreservationsService {
    public void processingHotelreservations(BookingRequestDto bookingRequestDto);
}
