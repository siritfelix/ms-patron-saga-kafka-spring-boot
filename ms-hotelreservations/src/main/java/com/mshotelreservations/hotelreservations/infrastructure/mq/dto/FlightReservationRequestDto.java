package com.mshotelreservations.hotelreservations.infrastructure.mq.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mshotelreservations.hotelreservations.domain.FlightReservation;

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

}
