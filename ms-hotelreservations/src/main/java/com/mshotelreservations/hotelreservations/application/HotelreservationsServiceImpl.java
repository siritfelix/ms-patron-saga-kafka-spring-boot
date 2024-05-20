package com.mshotelreservations.hotelreservations.application;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.mshotelreservations.hotelreservations.domain.service.HotelreservationsService;
import com.mshotelreservations.hotelreservations.infrastructure.mq.MessageProducer;
import com.mshotelreservations.hotelreservations.infrastructure.mq.dto.BookingRequestDto;
import com.mshotelreservations.hotelreservations.shared.StatusGeneral;
import com.mshotelreservations.hotelreservations.shared.config.KafkaProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class HotelreservationsServiceImpl implements HotelreservationsService {
    private final MessageProducer messageProducer;
    private final KafkaProperties kafkaProperties;

    @Override
    public void processingHotelreservations(BookingRequestDto bookingRequestDto) {
        log.info("Start processingHotelreservations:{}", bookingRequestDto.getId());
        try {
            //Thread.sleep(1000);
        } catch (Exception e) {

        }
        if (StatusGeneral.PROCESING.toString().equals(bookingRequestDto.getCarRental().getStatus())) {
            String result = processingHotelreservations();
            if (result.equals(StatusGeneral.OK.toString())) {
                bookingRequestDto.getHotelReservation().setStatus(StatusGeneral.OK.toString());
                log.info("Ok processingHotelreservationsId:{}", bookingRequestDto.getId());
                messageProducer.sendMessage(kafkaProperties.getProducer().getTopic(), bookingRequestDto);
            } else {
                bookingRequestDto.getHotelReservation().setStatus(StatusGeneral.ERROR.toString());
                log.info("Error processingHotelreservationsId:{}", bookingRequestDto.getId());
                messageProducer.sendMessage(kafkaProperties.getProducer().getTopicReturn(), bookingRequestDto);
            }
        } else {
            bookingRequestDto.getHotelReservation().setStatus(StatusGeneral.CANCELLED.toString());
            log.info("Error processingHotelreservationsId:{}", bookingRequestDto.getId());
            messageProducer.sendMessage(kafkaProperties.getProducer().getTopicReturn(), bookingRequestDto);
        }
        log.info("End processingHotelreservationsId:{}", bookingRequestDto.getId());
    }

    private String processingHotelreservations() {
        Random rand = new Random();
        int randNumber = rand.nextInt(100);
        log.info("randNumber:{}", randNumber);
        return (randNumber > 30) ? StatusGeneral.OK.toString() : StatusGeneral.ERROR.toString();

    }

}
