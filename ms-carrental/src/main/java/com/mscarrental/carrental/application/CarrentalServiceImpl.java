package com.mscarrental.carrental.application;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.mscarrental.carrental.domain.service.CarrentalService;
import com.mscarrental.carrental.infrastructure.mq.MessageProducer;
import com.mscarrental.carrental.infrastructure.mq.dto.BookingRequestDto;
import com.mscarrental.carrental.shared.StatusGeneral;
import com.mscarrental.carrental.shared.config.KafkaProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarrentalServiceImpl implements CarrentalService {
    private final MessageProducer messageProducer;
    private final KafkaProperties kafkaProperties;

    @Override
    public void processingCarrental(BookingRequestDto bookingRequestDto) {
        log.info("Start processingCarrental:{}", bookingRequestDto.getId());
        try {
            //Thread.sleep(1000);
        } catch (Exception e) {

        }
        String result = processingHotelreservations();

        if (result.equals(StatusGeneral.OK.toString())) {
            bookingRequestDto.getCarRental().setStatus(StatusGeneral.OK.toString());
            log.info("Ok processingCarrentalId:{}", bookingRequestDto.getId());
            messageProducer.sendMessage(kafkaProperties.getProducer().getTopic(), bookingRequestDto);
        } else {
            bookingRequestDto.getCarRental().setStatus(StatusGeneral.ERROR.toString());
            log.info("Error processingCarrentalId:{}", bookingRequestDto.getId());
            messageProducer.sendMessage(kafkaProperties.getProducer().getTopicReturn(), bookingRequestDto);
        }
        log.info("End processingCarrental:{}", bookingRequestDto);
    }

    private String processingHotelreservations() {
        Random rand = new Random();
        int randNumber = rand.nextInt(100);
        log.info("randNumber:{}", randNumber);
        return (randNumber > 30) ? StatusGeneral.OK.toString() : StatusGeneral.ERROR.toString();
    }

}
