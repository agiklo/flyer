package pl.matcodem.trackingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.trackingservice.entity.Flight;
import pl.matcodem.trackingservice.request.FlightCreateRequest;
import pl.matcodem.trackingservice.service.FlightAdministrationService;

@RestController
@RequiredArgsConstructor
public class AdministratorController {

    private final FlightAdministrationService flightService;

    @PostMapping("admin/flights")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightCreateRequest request) {
        Flight createdFlight = flightService.createFlight(request);

        if (createdFlight != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
