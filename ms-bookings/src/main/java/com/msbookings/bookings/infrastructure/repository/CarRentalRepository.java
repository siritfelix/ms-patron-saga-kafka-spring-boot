package com.msbookings.bookings.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msbookings.bookings.infrastructure.repository.entity.CarRentalEntity;

import jakarta.transaction.Transactional;

public interface CarRentalRepository extends JpaRepository<CarRentalEntity, String> {
    @Transactional
    @Modifying
    @Query("UPDATE CarRentalEntity e SET e.status = :statusNew WHERE e.id = :id")
    public void upDateStatus(String id, String statusNew);
}
