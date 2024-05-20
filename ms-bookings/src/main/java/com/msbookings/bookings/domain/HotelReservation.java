package com.msbookings.bookings.domain;

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
public class HotelReservation {
    private String id;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String address;
    private String reservationType;
}
