package com.msbookings.bookings.infrastructure.rest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msbookings.bookings.domain.FlightReservation;
import com.msbookings.bookings.infrastructure.repository.entity.FlightReservationEntity;

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
public class FlightReservationRequestDto implements Serializable {
    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;
    private Integer attempts;

    public FlightReservation toFlightReservation() {
        return FlightReservation.builder().id(id)
                .status(status)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }

    public FlightReservationRequestDto(FlightReservation flightReservation) {
        this.id = flightReservation.getId();
        this.status = flightReservation.getStatus();
        this.fromDate = flightReservation.getFromDate();
        this.toDate = flightReservation.getToDate();

    }

    public FlightReservationRequestDto(FlightReservationEntity flightReservation) {
        this.id = flightReservation.getId();
        this.status = flightReservation.getStatus();
        this.fromDate = flightReservation.getFromDate();
        this.toDate = flightReservation.getToDate();
        this.attempts = 0;

    }
}
