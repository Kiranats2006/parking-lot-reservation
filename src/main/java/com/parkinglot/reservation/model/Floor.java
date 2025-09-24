package com.parkinglot.reservation.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<ParkingSlot> slots = new ArrayList<>();

    public Floor() {}

    public Floor(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ParkingSlot> getSlots() { return slots; }
    public void setSlots(List<ParkingSlot> slots) { this.slots = slots; }
}
