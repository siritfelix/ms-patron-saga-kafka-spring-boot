package com.mshotelreservations.hotelreservations.domain;

import java.time.LocalDateTime;

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
public class FlightReservation {
    private String id;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
