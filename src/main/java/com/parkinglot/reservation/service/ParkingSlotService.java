package com.parkinglot.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.repository.ParkingSlotRepository;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository slotRepository;

    public ParkingSlot createSlot(String slotNumber, String vehicleType, Floor floor) {
        ParkingSlot slot = new ParkingSlot(slotNumber, vehicleType, floor);
        return slotRepository.save(slot);
    }
}
