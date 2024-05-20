package com.msbookings.bookings.infrastructure.repository.entity;

import java.time.LocalDateTime;

import com.msbookings.bookings.domain.FlightReservation;

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
@Table(name = "flightreservation")
public class FlightReservationEntity {
    @Id
    private String id;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public FlightReservation toFlightReservation() {
        return FlightReservation.builder()
                .id(id)
                .status(status)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }

    public FlightReservationEntity(FlightReservation flightReservation) {
        this.id = flightReservation.getId();
        this.status = flightReservation.getStatus();
        this.fromDate = flightReservation.getFromDate();
        this.toDate = flightReservation.getToDate();
    }

}
