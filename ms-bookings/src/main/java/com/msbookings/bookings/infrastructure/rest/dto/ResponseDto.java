package com.msbookings.bookings.infrastructure.rest.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String code, String message) {

}
