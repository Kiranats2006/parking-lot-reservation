package com.parkinglot.reservation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.parkinglot.reservation.model.Floor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use our H2 config
@ActiveProfiles("test") // picks up application-test.properties
public class FloorRepositoryTest {

    @Autowired
    private FloorRepository floorRepository;

    @Test
    void testSaveAndFindById() {
        Floor floor = new Floor("Second Floor");
        Floor saved = floorRepository.save(floor);

        assertNotNull(saved.getId());
        assertEquals("Second Floor", saved.getName());

        Floor found = floorRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Second Floor", found.getName());
    }
}
