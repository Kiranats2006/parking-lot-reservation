package com.parkinglot.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglot.reservation.exception.ResourceNotFoundException;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.repository.FloorRepository;
import com.parkinglot.reservation.service.ParkingSlotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ParkingSlotControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ParkingSlotService slotService;

    @Mock
    private FloorRepository floorRepository;

    @InjectMocks
    private ParkingSlotController slotController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(slotController)
                .setControllerAdvice(new ExceptionControllerAdvice()) // handle exceptions
                .build();
    }

    @Test
    public void testCreateSlotSuccess() throws Exception {
        Floor floor = new Floor("Floor 1");

        ParkingSlot slot = new ParkingSlot("A1", "4-wheeler", floor);

        when(floorRepository.findById(1L)).thenReturn(Optional.of(floor));
        when(slotService.createSlot("A1", "4-wheeler", floor)).thenReturn(slot);

        mockMvc.perform(post("/slots")
                        .param("slotNumber", "A1")
                        .param("vehicleType", "4-wheeler")
                        .param("floorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotNumber").value("A1"))
                .andExpect(jsonPath("$.vehicleType").value("4-wheeler"));
    }

    @Test
    public void testCreateSlotFloorNotFound() throws Exception {
        when(floorRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/slots")
                        .param("slotNumber", "A1")
                        .param("vehicleType", "4-wheeler")
                        .param("floorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Floor not found with id 1"));
    }
}
