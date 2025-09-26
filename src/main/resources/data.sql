INSERT INTO parking_slot (id, slot_number, vehicle_type) VALUES (1, 'A1', '4-wheeler');
INSERT INTO parking_slot (id, slot_number, vehicle_type) VALUES (2, 'A2', '4-wheeler');
INSERT INTO parking_slot (id, slot_number, vehicle_type) VALUES (3, 'A3', '2-wheeler');
INSERT INTO parking_slot (id, slot_number, vehicle_type) VALUES (4, 'B1', '4-wheeler');

INSERT INTO reservation (id, vehicle_number, vehicle_type, start_time, end_time, total_cost, slot_id) 
VALUES (1, 'KA02AB1234', '4-wheeler', '2025-09-25T14:00:00', '2025-09-25T16:00:00', 60, 1);


