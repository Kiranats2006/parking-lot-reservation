package com.parkinglot.reservation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findBySlotAndEndTimeAfterAndStartTimeBefore(
        ParkingSlot slot, LocalDateTime startTime, LocalDateTime endTime
    );
}
