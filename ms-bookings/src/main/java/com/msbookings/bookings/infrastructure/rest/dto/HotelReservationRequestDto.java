package com.msbookings.bookings.infrastructure.rest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msbookings.bookings.domain.HotelReservation;
import com.msbookings.bookings.infrastructure.repository.entity.HotelReservationEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HotelReservationRequestDto implements Serializable {
    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;
    private String address;
    private String reservationType;
    private Integer attempts;

    public HotelReservation toHotelReservation() {
        return HotelReservation.builder().id(id)
                .status(status)
                .fromDate(fromDate)
                .toDate(toDate)
                .address(address)
                .reservationType(reservationType)
                .build();
    }

    public HotelReservationRequestDto(HotelReservation hotelReservation) {
        this.id = hotelReservation.getId();
        this.status = hotelReservation.getStatus();
        this.fromDate = hotelReservation.getFromDate();
        this.toDate = hotelReservation.getToDate();
        this.address = hotelReservation.getAddress();
        this.reservationType = hotelReservation.getReservationType();
    }

    public HotelReservationRequestDto(HotelReservationEntity hotelReservation) {
        this.id = hotelReservation.getId();
        this.status = hotelReservation.getStatus();
        this.fromDate = hotelReservation.getFromDate();
        this.toDate = hotelReservation.getToDate();
        this.address = hotelReservation.getAddress();
        this.reservationType = hotelReservation.getReservationType();
        this.attempts = 0;
    }
}
