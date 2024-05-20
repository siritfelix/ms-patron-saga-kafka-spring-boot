package com.msbookings.bookings.shared.exception;



import com.msbookings.bookings.infrastructure.rest.dto.ResponseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class NotFoundException extends RuntimeException {

    private final ResponseDto responseDto;

}
