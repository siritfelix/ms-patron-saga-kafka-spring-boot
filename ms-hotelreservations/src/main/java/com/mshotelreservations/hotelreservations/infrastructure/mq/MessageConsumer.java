package com.mshotelreservations.hotelreservations.infrastructure.mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mshotelreservations.hotelreservations.domain.service.HotelreservationsService;
import com.mshotelreservations.hotelreservations.infrastructure.mq.dto.BookingRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final HotelreservationsService hotelreservationsService;

    @KafkaListener(topics = "${kafka-config.consumer.topic}", groupId = "${kafka-config.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderItem(ConsumerRecord<String, BookingRequestDto> consumerRecord) {

        log.info("topic = %s, partition = %d, offset = %d, customer = %s, country = %s\n",
                consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.key(), consumerRecord.value());
        BookingRequestDto bookingRequestDto;
        bookingRequestDto = consumerRecord.value();
        log.info("Mensaje recibido desde Kafka broker ={} ",
                bookingRequestDto.toString());
        hotelreservationsService.processingHotelreservations(consumerRecord.value());

    }
}
