package com.parkinglot.reservation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.reservation.exception.ResourceNotFoundException;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;
import com.parkinglot.reservation.repository.ParkingSlotRepository;
import com.parkinglot.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ParkingSlotRepository slotRepository;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestParam String vehicleNumber, @RequestParam String vehicleType, @RequestParam Long slotId, @RequestParam String startTime, @RequestParam String endTime) {

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        ParkingSlot slot = slotRepository.findById(slotId).orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + slotId));

        Reservation reservation = reservationService.createReservation(vehicleNumber, vehicleType, start, end, slot);
        return ResponseEntity.ok(reservation);
    }
    
    @GetMapping("/availability")
    public ResponseEntity<?> getAvailability(@RequestParam String startTime, @RequestParam String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        List<ParkingSlot> allSlots = slotRepository.findAll();
        List<ParkingSlot> available = reservationService.getAvailableSlots(start, end, allSlots);

        if (available.isEmpty()) {
            return ResponseEntity.ok("No slots available for the selected time. Check console for next available slots.");
        }
        return ResponseEntity.ok(available);
    }
}
