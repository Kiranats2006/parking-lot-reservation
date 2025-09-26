package com.parkinglot.reservation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.ParkingSlot;

@DataJpaTest
public class ParkingSlotRepositoryTest {

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Test
    public void testSaveAndFindSlot() {
        Floor floor = floorRepository.save(new Floor("Ground Floor"));
        ParkingSlot slot = new ParkingSlot("A1", "4-wheeler", floor);
        ParkingSlot saved = slotRepository.save(slot);

        assertNotNull(saved.getId());

        ParkingSlot fetched = slotRepository.findById(saved.getId()).orElse(null);
        assertNotNull(fetched);
        assertEquals("A1", fetched.getSlotNumber());
        assertEquals("4-wheeler", fetched.getVehicleType());
        assertEquals(floor.getName(), fetched.getFloor().getName());
    }
}
