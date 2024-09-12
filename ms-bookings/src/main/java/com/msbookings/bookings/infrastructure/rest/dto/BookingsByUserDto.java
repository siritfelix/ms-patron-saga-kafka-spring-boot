package com.msbookings.bookings.infrastructure.rest.dto;

import java.util.List;

import com.msbookings.bookings.domain.Booking;

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
public class BookingsByUserDto {
    private UserDto user;
    private List<BookingResponseDto> bookings;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class BookingResponseDto {
        private String id;
        private String status;
        private FlightReservationRequestDto flightReservation;
        private HotelReservationRequestDto hotelReservation;
        private CarRentalRequestDto carRental;
    }

    public BookingsByUserDto(List<Booking> bookings) {
        this.user = new UserDto(bookings.get(0).getUser());
        this.bookings = bookings.stream()
                .map(booking -> new BookingResponseDto(booking.getId(), booking.getStatus(),
                        new FlightReservationRequestDto(booking.getFlightReservation()),
                        new HotelReservationRequestDto(booking.getHotelReservation()),
                        new CarRentalRequestDto(booking.getCarRental())))
                .toList();
    }
}
