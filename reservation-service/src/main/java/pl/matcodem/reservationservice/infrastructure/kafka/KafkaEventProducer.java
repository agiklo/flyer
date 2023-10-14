package pl.matcodem.reservationservice.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.matcodem.reservationservice.application.events.Event;

@Service
public class KafkaEventProducer {
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, Event event) {
        kafkaTemplate.send(topic, event);
    }
}


