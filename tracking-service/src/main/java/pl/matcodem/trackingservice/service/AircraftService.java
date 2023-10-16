package pl.matcodem.trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.matcodem.trackingservice.entity.Aircraft;
import pl.matcodem.trackingservice.repository.AircraftRepository;
import pl.matcodem.trackingservice.response.AircraftResponse;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public Page<AircraftResponse> getAllAircrafts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Aircraft> aircrafts = aircraftRepository.findAll(pageable);

        return aircrafts.map(AircraftResponse::fromAircraft);
    }
}
