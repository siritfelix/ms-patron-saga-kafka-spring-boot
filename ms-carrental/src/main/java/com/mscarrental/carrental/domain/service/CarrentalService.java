package com.mscarrental.carrental.domain.service;

import com.mscarrental.carrental.infrastructure.mq.dto.BookingRequestDto;

public interface CarrentalService {
    public void processingCarrental(BookingRequestDto bookingRequestDto);
}
