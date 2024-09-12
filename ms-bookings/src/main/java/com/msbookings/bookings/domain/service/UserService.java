package com.msbookings.bookings.domain.service;

import java.util.List;

import com.msbookings.bookings.domain.Booking;

public interface UserService {
    List<Booking> getUserByEmail(String email);
}
