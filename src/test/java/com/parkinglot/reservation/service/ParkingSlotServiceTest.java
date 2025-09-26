package com.parkinglot.reservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.repository.ParkingSlotRepository;

class ParkingSlotServiceTest {

    @Mock
    private ParkingSlotRepository slotRepository;

    @InjectMocks
    private ParkingSlotService slotService;

    private Floor floor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        floor = new Floor();
        floor.setName("Floor 1");
    }

    @Test
    void testCreateSlot() {
        ParkingSlot slot = new ParkingSlot("A1", "4-wheeler", floor);

        when(slotRepository.save(any(ParkingSlot.class))).thenReturn(slot);

        ParkingSlot created = slotService.createSlot("A1", "4-wheeler", floor);

        assertNotNull(created);
        assertEquals("A1", created.getSlotNumber());
        assertEquals("4-wheeler", created.getVehicleType());
        assertEquals(floor, created.getFloor());

        verify(slotRepository, times(1)).save(any(ParkingSlot.class));
    }
}
