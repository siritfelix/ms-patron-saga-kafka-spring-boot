package com.msflightsreservations.flightsreservations.application;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.msflightsreservations.flightsreservations.domain.service.FlightsreservationsService;
import com.msflightsreservations.flightsreservations.infrastructure.mq.MessageProducer;
import com.msflightsreservations.flightsreservations.infrastructure.mq.dto.BookingRequestDto;
import com.msflightsreservations.flightsreservations.shared.StatusGeneral;
import com.msflightsreservations.flightsreservations.shared.config.KafkaProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlightsreservationsServiceImpl implements FlightsreservationsService {
    private final MessageProducer messageProducer;
    private final KafkaProperties kafkaProperties;

    @Override
    public void processingFlightreservation(BookingRequestDto bookingRequestDto) {
        log.info("Start processingFlightreservation:{}", bookingRequestDto.getId());
        try {
            //Thread.sleep(1000);
        } catch (Exception e) {

        }

        if (StatusGeneral.PROCESING.toString().equals(bookingRequestDto.getHotelReservation().getStatus())) {
            String result = processingFligthreservation();
            bookingRequestDto.getFlightReservation()
                    .setAttempts(bookingRequestDto.getFlightReservation().getAttempts() + 1);
            if (result.equals(StatusGeneral.OK.toString())) {
                bookingRequestDto.getFlightReservation().setStatus(StatusGeneral.OK.toString());
                log.info("Ok processingFlightreservationId:{}", bookingRequestDto.getId());
                messageProducer.sendMessage(kafkaProperties.getProducer().getTopic(), bookingRequestDto);
            } else {
                bookingRequestDto.getFlightReservation().setStatus(StatusGeneral.ERROR.toString());
                log.info("Error processingFlightreservationId:{}", bookingRequestDto.getId());
                if (bookingRequestDto.getFlightReservation().getAttempts() < kafkaProperties.getAttempts()) {
                    log.info("Error and Retrived, processingFlightreservationId:{}", bookingRequestDto.getId());
                    messageProducer.sendMessage(kafkaProperties.getConsumer().getTopic(), bookingRequestDto);
                } else {
                    log.info("Error processingFlightreservationId:{}", bookingRequestDto.getId());
                    messageProducer.sendMessage(kafkaProperties.getProducer().getTopicReturn(), bookingRequestDto);
                }
            }
        } else {
            bookingRequestDto.getFlightReservation().setStatus(StatusGeneral.CANCELLED.toString());
            log.info("Error processingFlightreservationId:{}", bookingRequestDto.getId());
            messageProducer.sendMessage(kafkaProperties.getProducer().getTopicReturn(), bookingRequestDto);
        }

        log.info("End processingFlightreservation:{}", bookingRequestDto);
    }

    private String processingFligthreservation() {
        Random rand = new Random();
        int randNumber = rand.nextInt(100);
        log.info("randNumber:{}", randNumber);
        return (randNumber > 30) ? StatusGeneral.OK.toString() : StatusGeneral.ERROR.toString();
    }

}
