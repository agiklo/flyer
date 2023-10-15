package pl.matcodem.trackingservice.service.external.api.airport;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExternalAirportDatabase {

    private final WebClient.Builder webClientBuilder;

    @Value("${external.api.airports.url}")
    private String apiUrl;

    public Mono<AirportInfo> getAirportInfoByIcaoCode(String icaoCode) {
        return webClientBuilder
                .baseUrl(apiUrl)
                .build()
                .get()
                .uri("/{icaoCode}.json", icaoCode)
                .retrieve()
                .bodyToMono(AirportInfo.class);
    }
}