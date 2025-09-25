package com.parkinglot.reservation.service;

import com.parkinglot.reservation.exception.InvalidRequestException;
import com.parkinglot.reservation.exception.SlotUnavailableException;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;
import com.parkinglot.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private final Map<String, Integer> rates = new HashMap<>() {{
        put("2-wheeler", 20);
        put("4-wheeler", 30);
    }};

    public Reservation createReservation(String vehicleNumber, String vehicleType, LocalDateTime startTime, LocalDateTime endTime, ParkingSlot slot) {

        //time validation
        if (startTime.isAfter(endTime)) {
            throw new InvalidRequestException("Start time must be before end time");
        }
        
        //reservation exceeding 24 hours
        Duration duration = Duration.between(startTime, endTime);
        if (duration.toHours() > 24) {
            throw new InvalidRequestException("Reservation cannot exceed 24 hours");
        }

        //vehicle number validation
        if (!vehicleNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}")) {
            throw new InvalidRequestException("Vehicle number format invalid: " + vehicleNumber);
        }

        //overlapping reservations validation
        List<Reservation> overlapping = reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, startTime, endTime);
        if (!overlapping.isEmpty()) {
            throw new SlotUnavailableException("Slot is already reserved for this time range");
        }

        //calculate cost
        long hours = duration.toMinutes() / 60;
        if (duration.toMinutes() % 60 != 0) hours++;
        int rate = rates.getOrDefault(vehicleType, 30);
        int totalCost = (int) (hours * rate);

        //create obj and save
        Reservation reservation = new Reservation(vehicleNumber, vehicleType, startTime, endTime, totalCost, slot);
        return reservationRepository.save(reservation);
    }
}
