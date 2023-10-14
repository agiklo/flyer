package pl.matcodem.reservationservice.infrastructure.kafka;

public enum KafkaTopics {
    RESERVATION_CREATED("reservation-created-topic");

    private final String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}

