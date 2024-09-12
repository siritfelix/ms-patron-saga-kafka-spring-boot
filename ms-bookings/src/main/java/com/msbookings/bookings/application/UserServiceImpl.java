package com.msbookings.bookings.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.msbookings.bookings.domain.Booking;
import com.msbookings.bookings.domain.service.UserService;
import com.msbookings.bookings.infrastructure.repository.BookingRepository;
import com.msbookings.bookings.infrastructure.repository.UserEntityRepository;
import com.msbookings.bookings.infrastructure.repository.entity.BookingEntity;
import com.msbookings.bookings.infrastructure.repository.entity.UserEntity;
import com.msbookings.bookings.infrastructure.rest.dto.ResponseDto;
import com.msbookings.bookings.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getUserByEmail(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ResponseDto.builder().build()));
        List<BookingEntity> bookingEntities = bookingRepository.findByUser(userEntity);
        if (bookingEntities.isEmpty()) {
            bookingEntities.add(new BookingEntity(null, null, null, null, null, userEntity, null, null));
        }
        return bookingEntities.stream().map(booking -> booking.toBooking()).collect(Collectors.toList());
    }
}
