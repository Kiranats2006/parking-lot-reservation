package com.parkinglot.reservation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String reservationToken;
    private String vehicleNumber;
    private String vehicleType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalCost;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private ParkingSlot slot;

    public Reservation(String vehicleNumber, String vehicleType,LocalDateTime startTime, LocalDateTime endTime,int totalCost, ParkingSlot slot) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.slot = slot;
        this.reservationToken = UUID.randomUUID().toString();
    }
}
