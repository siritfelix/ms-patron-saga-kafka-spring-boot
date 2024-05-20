package com.msbookings.bookings.domain.service;

import java.util.List;

import com.msbookings.bookings.domain.Booking;

public interface BookingService {

    public Booking createBooking(Booking booking);

    public Booking getBooking(String id);

    public void upDateBooking(Booking booking);

    public List<Booking> getAllBookings();

}
