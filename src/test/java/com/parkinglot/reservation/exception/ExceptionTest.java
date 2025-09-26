package com.parkinglot.reservation.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ExceptionTest {

    @Test
    void testInvalidRequestExceptionMessage() {
        String msg = "Invalid request!";
        InvalidRequestException ex = assertThrows(InvalidRequestException.class, () -> {
            throw new InvalidRequestException(msg);
        });
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testResourceNotFoundExceptionMessage() {
        String msg = "Resource not found!";
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(msg);
        });
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void testSlotUnavailableExceptionMessage() {
        String msg = "Slot unavailable!";
        SlotUnavailableException ex = assertThrows(SlotUnavailableException.class, () -> {
            throw new SlotUnavailableException(msg);
        });
        assertEquals(msg, ex.getMessage());
    }
}
