package com.msbookings.bookings.infrastructure.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msbookings.bookings.domain.service.BookingService;
import com.msbookings.bookings.infrastructure.rest.dto.BookingRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = BookingController.URI)
@RequiredArgsConstructor
@RestController
public class BookingController {
    public static final String URI = "booking";
    public static final String ALL = "all";

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingRequestDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        log.info(bookingRequestDto.toString());
        return ResponseEntity.ok()
                .body(new BookingRequestDto(bookingService.createBooking(bookingRequestDto.toBooking())));
    }

    @GetMapping
    public ResponseEntity<BookingRequestDto> getBooking(@RequestParam String id) {
        return ResponseEntity.ok()
                .body(new BookingRequestDto(bookingService.getBooking(id)));
    }

    @GetMapping(path = ALL)
    public ResponseEntity<List<BookingRequestDto>> getAllBookings() {
        return ResponseEntity.ok()
                .body(bookingService.getAllBookings().stream()
                        .map(booking -> new BookingRequestDto(booking)).collect(Collectors.toList()));
    }
}
