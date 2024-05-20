package com.msbookings.bookings.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msbookings.bookings.infrastructure.repository.entity.FlightReservationEntity;

import jakarta.transaction.Transactional;

public interface FlightReservationRepository extends JpaRepository<FlightReservationEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE FlightReservationEntity e SET e.status = :statusNew WHERE e.id = :id")
    public void upDateStatus(String id, String statusNew);
}
