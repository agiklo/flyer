package pl.matcodem.trackingservice.events.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.matcodem.trackingservice.events.FlightDelayEvent;
import pl.matcodem.trackingservice.exceptions.FlightNotFoundException;
import pl.matcodem.trackingservice.repository.FlightRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightDelayEventHandler {

    private final FlightRepository flightRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFlightDelayedEvent(FlightDelayEvent event) {
        var flight = flightRepository.findById(event.designatorCode())
                .orElseThrow(() -> new FlightNotFoundException("Flight with designator code %s not found!"
                        .formatted(event.designatorCode())));
        log.info("Flight %s has been delayed by %s minutes".formatted(flight.getDesignatorCode(), event.delayTimeMinutes()));
        // TODO: Send event to the kafka
    }
}
