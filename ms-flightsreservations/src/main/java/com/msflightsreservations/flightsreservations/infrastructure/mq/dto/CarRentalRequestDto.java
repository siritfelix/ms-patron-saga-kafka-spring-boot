package com.msflightsreservations.flightsreservations.infrastructure.mq.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msflightsreservations.flightsreservations.domain.CarRental;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CarRentalRequestDto implements Serializable {
    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;
    private String typeOfCar;
    private Integer attempts;

    public CarRental toCarRental() {
        return CarRental.builder().id(id)
                .fromDate(fromDate)
                .toDate(toDate)
                .typeOfCar(typeOfCar)
                .build();
    }

    public CarRentalRequestDto(CarRental carRental) {
        this.id = carRental.getId();
        this.status = carRental.getStatus();
        this.fromDate = carRental.getFromDate();
        this.toDate = carRental.getToDate();
        this.typeOfCar = carRental.getTypeOfCar();
    }

}
