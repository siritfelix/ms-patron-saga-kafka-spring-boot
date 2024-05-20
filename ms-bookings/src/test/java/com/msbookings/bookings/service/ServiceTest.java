package com.msbookings.bookings.service;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.msbookings.bookings.infrastructure.rest.dto.BookingRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceTest {

    @Test
    public void test1() {
        String json = "{\"id\":\"8cf60d10-f6c7-4446-85f5-1886ed66a12e\",\"status\":\"PROCESING\",\"user\":{\"id\":\"39595b37-02c9-4cd8-8e1e-fec6a79cd94e\",\"name\":\"Sirit\",\"lastName\":\"Sirit\",\"document\":\"12345678\",\"email\":\"felix@sirit.com\"},\"flightReservation\":{\"id\":\"9f217996-5fed-4c2f-b56b-7b9bed1af807\",\"status\":\"PROCESING\",\"fromDate\":\"2024-01-25T15:55:01\",\"toDate\":\"2024-01-25T15:55:01\",\"attempts\":0},\"hotelReservation\":{\"id\":\"72c617aa-5032-4403-9a9b-9e12935e8078\",\"status\":\"PROCESING\",\"fromDate\":\"2024-01-25T15:55:01\",\"toDate\":\"2024-01-25T15:55:01\",\"address\":\"velitas 2\",\"reservationType\":\"2 personas\",\"attempts\":0},\"carRental\":{\"id\":\"2973e5df-f126-4ce7-82c9-5692e6ba4816\",\"status\":\"PROCESING\",\"fromDate\":\"2024-01-25T15:55:01\",\"toDate\":\"2024-01-25T15:55:01\",\"typeOfCar\":\"Sedan\",\"attempts\":0},\"attempts\":0}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        BookingRequestDto bookingRequestDto;
        try {

            bookingRequestDto = mapper.readValue(json,
                    BookingRequestDto.class);
            log.info("Convertido = " + bookingRequestDto.toString());
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
        }
    }
}
