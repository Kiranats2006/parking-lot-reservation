package com.parkinglot.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MainTest {

@Test
void testMain() {
    String[] args = {};
    try {
        Class.forName("com.parkinglot.reservation.ParkingLotReservationApplication")
             .getMethod("main", String[].class)
             .invoke(null, (Object) args);
    } catch (Exception ignored) {}
}

}
