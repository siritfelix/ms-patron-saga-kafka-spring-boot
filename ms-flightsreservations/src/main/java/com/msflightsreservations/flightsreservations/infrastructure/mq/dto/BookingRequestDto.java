package com.msflightsreservations.flightsreservations.infrastructure.mq.dto;

import java.io.Serializable;

import com.msflightsreservations.flightsreservations.domain.Booking;

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
public class BookingRequestDto implements Serializable {
    private String id;
    private String status;
    private UserDto user;
    private FlightReservationRequestDto flightReservation;
    private HotelReservationRequestDto hotelReservation;
    private CarRentalRequestDto carRental;
    private Integer attempts;

    public Booking toBooking() {
        return Booking.builder()
                .id(id)
                .user(user.toUser())
                .flightReservation(flightReservation.toFlightReservation())
                .hotelReservation(hotelReservation.toHotelReservation())
                .carRental(carRental.toCarRental())
                .build();
    }

    public BookingRequestDto(Booking booking) {
        this.id = booking.getId();
        this.status = booking.getStatus();
        this.user = new UserDto(booking.getUser());
        this.flightReservation = new FlightReservationRequestDto(booking.getFlightReservation());
        this.hotelReservation = new HotelReservationRequestDto(booking.getHotelReservation());
        this.carRental = new CarRentalRequestDto(booking.getCarRental());

    }
}
