package com.msbookings.bookings.infrastructure.mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.msbookings.bookings.infrastructure.rest.dto.BookingRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    @KafkaListener(topics = "${kafka-config.consumer.topic}", groupId = "${kafka-config.consumer.group-id}")
    public void consumeOrderItem(ConsumerRecord<String, BookingRequestDto> consumerRecord) {

        log.info("topic = %s, partition = %d, offset = %d, customer = %s, country = %s\n",
                consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.key(), consumerRecord.value().getId());
        BookingRequestDto bookingRequestDto;
        bookingRequestDto = consumerRecord.value();
        log.info("Mensaje recibido desde Kafka broker ={} ", bookingRequestDto.toString());
    }
}
