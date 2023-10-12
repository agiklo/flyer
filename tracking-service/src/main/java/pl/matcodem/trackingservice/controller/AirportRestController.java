package pl.matcodem.trackingservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.trackingservice.response.AirportResponse;
import pl.matcodem.trackingservice.service.AirportService;

/**
 * A REST controller for handling airport-related requests.
 */
@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
@Api(tags = "Airport Controller")
public class AirportRestController {

    private final AirportService airportService;

    /**
     * Get a paginated list of airports.
     *
     * @param page Page number (default is 0).
     * @param size Number of items per page (default is 10).
     * @return A page of AirportResponse entities.
     */
    @GetMapping
    @ApiOperation(value = "Get a paginated list of airports")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval of airports"),
            @ApiResponse(code = 404, message = "No airports found")
    })
    public Page<AirportResponse> getAllAirports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return airportService.getAllAirports(page, size);
    }

    /**
     * Get an airport by its ICAO code.
     *
     * @param icaoCode The ICAO code of the airport.
     * @return The AirportResponse for the specified ICAO code.
     */
    @GetMapping("/{icaoCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an airport by its ICAO code")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval of the airport"),
            @ApiResponse(code = 404, message = "Airport not found")
    })
    public AirportResponse getAirportByIcaoCode(@PathVariable("icaoCode") String icaoCode) {
        return airportService.getAirportByIcaoCode(icaoCode);
    }
}

