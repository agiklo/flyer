package pl.matcodem.trackingservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.matcodem.trackingservice.response.AircraftResponse;
import pl.matcodem.trackingservice.service.AircraftService;

@RestController
@RequestMapping("/aircrafts")
@RequiredArgsConstructor
@Api(tags = "Aircraft Controller")
public class AircraftRestController {

    private final AircraftService aircraftService;

    @GetMapping
    @ApiOperation(value = "Get a paginated list of aircrafts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval of aircrafts"),
            @ApiResponse(code = 404, message = "No aircraft found")
    })
    public Page<AircraftResponse> getAllAircrafts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return aircraftService.getAllAircrafts(page, size);
    }
}
