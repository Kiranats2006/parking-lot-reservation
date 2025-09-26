package com.parkinglot.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.reservation.exception.ResourceNotFoundException;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.repository.FloorRepository;
import com.parkinglot.reservation.service.ParkingSlotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService slotService;

    @Autowired
    private FloorRepository floorRepository;

    //POST /slots?slotNumber=A1&vehicleType=4-wheeler&floorId=1
    @PostMapping
    public ResponseEntity<?> createSlot(@RequestParam @Valid String slotNumber,
                                        @RequestParam @Valid String vehicleType,
                                        @RequestParam Long floorId) {
        //Checking if floor exists
        Floor floor = floorRepository.findById(floorId).orElseThrow(() -> new ResourceNotFoundException("Floor not found with id " + floorId));

        ParkingSlot slot = slotService.createSlot(slotNumber, vehicleType, floor);
        return ResponseEntity.ok(slot);
    }
}
