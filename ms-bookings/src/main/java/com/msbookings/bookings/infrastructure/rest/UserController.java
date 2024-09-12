package com.msbookings.bookings.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msbookings.bookings.domain.service.UserService;
import com.msbookings.bookings.infrastructure.rest.dto.BookingsByUserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(path = UserController.URI)
@RequiredArgsConstructor
@RestController
public class UserController {
    public static final String URI = "user";
    private final UserService userService;

    @GetMapping
    public ResponseEntity<BookingsByUserDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok()
                .body(new BookingsByUserDto(userService.getUserByEmail(email)));
    }
}
