package com.msbookings.bookings.infrastructure.repository.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msbookings.bookings.domain.Booking;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class BookingEntity {
    @Id
    private String id;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    private FlightReservationEntity flightReservation;

    @OneToOne(cascade = CascadeType.ALL)
    private HotelReservationEntity hotelReservation;

    @OneToOne(cascade = CascadeType.ALL)
    private CarRentalEntity carRental;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime createAt;
    
    @UpdateTimestamp
    private LocalDateTime upDateAt;

    @PreUpdate
    protected void onUpdate() {
        upDateAt = LocalDateTime.now();
    }

    public Booking toBooking() {
        return Booking.builder()
                .id(id)
                .status(status)
                .flightReservation(flightReservation.toFlightReservation())
                .hotelReservation(hotelReservation.toHotelReservation())
                .carRental(carRental.toCarRental()).user(user.toUser())
                .build();
    }

    public BookingEntity(Booking booking) {
        this.id = booking.getId();
        this.status = booking.getStatus();
        this.flightReservation = new FlightReservationEntity(booking.getFlightReservation());
        this.hotelReservation = new HotelReservationEntity(booking.getHotelReservation());
        this.carRental = new CarRentalEntity(booking.getCarRental());
        this.user = new UserEntity(booking.getUser());

    }

}
