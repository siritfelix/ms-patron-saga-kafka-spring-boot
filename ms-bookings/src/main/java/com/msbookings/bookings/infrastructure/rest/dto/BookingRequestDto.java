package com.msbookings.bookings.infrastructure.rest.dto;

import java.io.Serializable;

import com.msbookings.bookings.domain.Booking;
import com.msbookings.bookings.infrastructure.repository.entity.BookingEntity;

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
                .status(status)
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

    public BookingRequestDto(BookingEntity booking) {
        this.id = booking.getId();
        this.status = booking.getStatus();
        this.user = new UserDto(booking.getUser());
        this.flightReservation = new FlightReservationRequestDto(booking.getFlightReservation());
        this.hotelReservation = new HotelReservationRequestDto(booking.getHotelReservation());
        this.carRental = new CarRentalRequestDto(booking.getCarRental());
        this.attempts = 0;
    }

}
