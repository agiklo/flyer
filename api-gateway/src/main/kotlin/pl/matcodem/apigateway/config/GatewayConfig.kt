package pl.matcodem.apigateway.config
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val LB_TRACKING_SERVICE = "lb://tracking-service"
private const val LB_RESERVATION_SERVICE = "lb://reservation-service"

@Configuration
class GatewayConfig {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
                .route("allFlightsRoute") {
                    it.path("/api/v1/flights/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("flightsByDepartureAndArrivalAndDateRoute") {
                    it.path("/api/v1/flights/by-departure-and-arrival-icao-and-date/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("flightsByDepartureIcaoRoute") {
                    it.path("/api/v1/flights/by-departure-icao/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("flightsByDepartureAndArrivalIcaoRoute") {
                    it.path("/api/v1/flights/by-departure-and-arrival-icao/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("flightsByArrivalIcaoRoute") {
                    it.path("/api/v1/flights/by-arrival-icao/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("flightByDesignatorCodeRoute") {
                    it.path("/api/v1/flights/by-designator-code/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("reservation-service") {
                    it.path("/api/v1/reservations/**")
                            .uri(LB_RESERVATION_SERVICE)
                }
                .route("findOnewayTripByDestinationAndDate") {
                    it.path("/api/v1/trips/oneway-trip/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .route("findRoundTripByDestinationAndDate") {
                    it.path("/api/v1/trips/round-trip/**")
                            .uri(LB_TRACKING_SERVICE)
                }
                .build()
    }
}
