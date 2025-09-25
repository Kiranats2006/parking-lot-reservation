package com.parkinglot.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.reservation.exception.InvalidRequestException;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.service.FloorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/floors")
public class FloorController {

    @Autowired
    private FloorService floorService;

    // POST /floors
    @PostMapping
    public ResponseEntity<Floor> createFloor(@RequestParam @Valid String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidRequestException("Floor name cannot be empty");
        }
        Floor floor = floorService.createFloor(name);
        return ResponseEntity.ok(floor);
    }
}
