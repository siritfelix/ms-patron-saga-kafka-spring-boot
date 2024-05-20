package com.msflightsreservations.flightsreservations.domain;

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
public class Booking {

    private User user;
    private String id;
    private String status;
    private FlightReservation flightReservation;
    private HotelReservation hotelReservation;
    private CarRental carRental;

}
