package com.msbookings.bookings.infrastructure.repository.entity;

import java.time.LocalDateTime;

import com.msbookings.bookings.domain.CarRental;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carrental")
public class CarRentalEntity {
    @Id
    private String id;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String typeOfCar;

    public CarRental toCarRental() {
        return CarRental.builder()
                .id(id)
                .status(status).fromDate(fromDate).toDate(toDate).typeOfCar(typeOfCar)
                .build();
    }

    public CarRentalEntity(CarRental carRental) {
        this.id = carRental.getId();
        this.status = carRental.getStatus();
        this.fromDate = carRental.getFromDate();
        this.toDate = carRental.getToDate();
        this.typeOfCar = carRental.getTypeOfCar();
    }

}
