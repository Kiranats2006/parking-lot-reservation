package com.parkinglot.reservation.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DummyExceptionController {

    @GetMapping("/notfound")
    public void throwNotFound() {
        throw new ResourceNotFoundException("Resource not found test");
    }

    @GetMapping("/unavailable")
    public void throwUnavailable() {
        throw new SlotUnavailableException("Slot unavailable test");
    }

    @GetMapping("/invalid")
    public void throwInvalid() {
        throw new InvalidRequestException("Invalid request test");
    }

    @GetMapping("/generic")
    public void throwGeneric() {
        throw new RuntimeException("Generic error test");
    }
}

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DummyExceptionController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/notfound")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found test"));
    }

    @Test
    public void testSlotUnavailableException() throws Exception {
        mockMvc.perform(get("/unavailable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("Slot unavailable test"));
    }

    @Test
    public void testInvalidRequestException() throws Exception {
        mockMvc.perform(get("/invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request test"));
    }

    @Test
    public void testGenericException() throws Exception {
        mockMvc.perform(get("/generic")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong: Generic error test"));
    }

    @Test
    void testHandleOptimisticLockDirectly() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ObjectOptimisticLockingFailureException ex =
                new ObjectOptimisticLockingFailureException("Conflict", new Object());

        ResponseEntity<String> response = handler.handleOptimisticLock(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Reservation conflict occurred. Please try again.", response.getBody());
    }

    
}
