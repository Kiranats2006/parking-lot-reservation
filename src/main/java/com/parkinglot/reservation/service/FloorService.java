package com.parkinglot.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.repository.FloorRepository;

@Service
public class FloorService {

    @Autowired
    private FloorRepository floorRepository;

    public Floor createFloor(String name) {
        Floor floor = new Floor(name);
        return floorRepository.save(floor);
    }
}
