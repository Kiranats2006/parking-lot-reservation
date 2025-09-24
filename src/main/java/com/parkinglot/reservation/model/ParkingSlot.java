package com.parkinglot.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotNumber;

    private String vehicleType; //"2-wheeler" or "4-wheeler"

    @ManyToOne
    @JoinColumn(name = "floor_id")
    @JsonIgnore
    private Floor floor;

    public ParkingSlot() {}

    public ParkingSlot(String slotNumber, String vehicleType, Floor floor) {
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
        this.floor = floor;
    }

    public Long getId() { return id; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
}
