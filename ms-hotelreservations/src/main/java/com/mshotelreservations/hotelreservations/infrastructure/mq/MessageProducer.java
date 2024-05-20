package com.mshotelreservations.hotelreservations.infrastructure.mq;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.mshotelreservations.hotelreservations.infrastructure.mq.dto.BookingRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, BookingRequestDto> template;

    public void sendMessage(String topic, BookingRequestDto bookingRequestDto) {
        log.info("Enviando en topico:{},  mensaje:{}", topic, bookingRequestDto);
        CompletableFuture<SendResult<String, BookingRequestDto>> future = this.template.send(topic,
                bookingRequestDto);
        future.whenComplete((result, e) -> {
            if (e == null) {
                log.info("Mensaje con key : {}, valor : {} enviado correctamente con offset {}",
                        result.getProducerRecord().key(), result.getProducerRecord().value().getId(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Mensaje con key : {}, valor : {} no enviado! caused by {}",
                        result.getProducerRecord().key(), result.getProducerRecord().value().getId(),
                        e.getMessage());
            }
        });
    }
}
