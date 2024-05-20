package com.msbookings.bookings.infrastructure.repository.entity;

import java.time.LocalDateTime;

import com.msbookings.bookings.domain.HotelReservation;

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
@Table(name = "hotelreservation")
public class HotelReservationEntity {
    @Id
    private String id;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String address;
    private String reservationType;

    public HotelReservation toHotelReservation() {
        return HotelReservation.builder().id(id).status(status).fromDate(fromDate).toDate(toDate).address(address)
                .reservationType(reservationType)
                .build();
    }

    public HotelReservationEntity(HotelReservation hotelReservation) {
        this.id = hotelReservation.getId();
        this.status = hotelReservation.getStatus();
        this.fromDate = hotelReservation.getFromDate();
        this.toDate = hotelReservation.getToDate();
        this.address = hotelReservation.getAddress();
        this.reservationType = hotelReservation.getReservationType();
    }

}
