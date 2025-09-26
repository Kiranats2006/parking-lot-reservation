# Parking Lot Reservation System

This is a backend project developed using Java and Spring Boot to manage a smart parking facility. The system allows administrators to manage floors and slots, while customers can reserve slots for specific time ranges. The backend ensures no overlapping reservations and calculates parking fees based on vehicle type and duration.

## Features Implemented

* **Floor & Slot Management**

  * Create parking floors.
  * Create parking slots under each floor.

* **Reservation Management**

  * Reserve a slot for a specific time range.
  * Automatic availability check to prevent double bookings.
  * Fee calculation based on vehicle type:

    * 2-wheeler: Rs. 20/hour
    * 4-wheeler: Rs. 30/hour
  * Partial hours are rounded up to the next full hour.
  * Optimistic locking implemented to prevent concurrent booking conflicts.

* **Availability & Details**

  * List available slots for a specific time range.
  * Retrieve reservation details by ID.
  * Cancel reservations by ID.

## Tech Stack

* Java 17 + Spring Boot 3
* H2 Database (runtime)
* JPA + Hibernate
* Bean Validation (`@Valid`) for input validation
* Global exception handling using `@ControllerAdvice`
* Lombok to reduce boilerplate code
* JUnit 5 + MockMvc for testing

## Running the Project Locally

1. Clone the repository:

```bash
git clone https://github.com/Kiranats2006/parking-lot-reservation.git
cd parking-lot-reservation
```

2. Build the project:

```bash
./mvnw clean package
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

The backend runs on `http://localhost:8080`.


## Running Tests

This project includes unit and integration tests for:

* Service layer (ReservationService, ParkingSlotService, FloorService)
* Controller layer (ReservationController)
* Exception handling (GlobalExceptionHandler)

### Run all tests

```bash
./mvnw test
```

This will execute all tests in `src/test/java` and print results in the console.

### Check test coverage

You can generate a coverage report using the JaCoCo plugin (if configured) or check Maven output. Tests cover:

* Valid and invalid reservation creation
* Slot availability checks
* Exception handling for:

  * `ResourceNotFoundException`
  * `SlotUnavailableException`
  * `InvalidRequestException`
  * `ObjectOptimisticLockingFailureException`
  * Generic runtime exceptions

Coverage reports (if JaCoCo is enabled) will be in:

```
target/site/jacoco/index.html
```

Open this file in a browser to see detailed coverage per class and method.


## API Endpoints

* **Create Reservation**

```http
POST /reservations
Params: vehicleNumber, vehicleType, slotId, startTime, endTime
```

* **Check Availability**

```http
GET /reservations/availability?startTime=<ISO_DATE>&endTime=<ISO_DATE>
```

* **Get Reservation Details**

```http
GET /reservations/{id}
```

* **Cancel Reservation**

```http
DELETE /reservations/{id}
```


## Deployment

Deployed backend to **[Add your deployment platform here, e.g., Render, Heroku, or Railway]**.

**Deployment Link:** [Parking Lot Reservation API](YOUR_DEPLOYMENT_LINK_HERE)


## Future Improvements

* pagination and sorting for availability endpoints.
* Integrate Swagger/OpenAPI for API documentation.
* Expand support for additional vehicle types and dynamic pricing.
* Integrate a frontend for a complete full-stack solution.


## Source Code & Repository

* All source code is available in this repository.
* README contains full setup, run, and testing instructions.

