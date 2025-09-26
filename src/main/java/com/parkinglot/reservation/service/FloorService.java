package com.parkinglot.reservation.service;

import org.springframework.stereotype.Service;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.repository.FloorRepository;

@Service
public class FloorService {

    private final FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    public Floor createFloor(String name) {
        Floor floor = new Floor(name);
        return floorRepository.save(floor);
    }
}
