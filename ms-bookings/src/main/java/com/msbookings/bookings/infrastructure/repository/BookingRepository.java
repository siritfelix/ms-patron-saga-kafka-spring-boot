package com.msbookings.bookings.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msbookings.bookings.infrastructure.repository.entity.BookingEntity;
import com.msbookings.bookings.infrastructure.repository.entity.UserEntity;

import jakarta.transaction.Transactional;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {
    @Transactional
    @Modifying
    @Query("UPDATE BookingEntity e SET e.status = :statusNew, e.upDateAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    public void upDateStatus(String id, String statusNew);

    List<BookingEntity> findByUser(UserEntity user);
}
