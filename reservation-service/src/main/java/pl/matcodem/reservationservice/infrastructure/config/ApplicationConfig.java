package pl.matcodem.reservationservice.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"pl.matcodem.reservationservice.util.annotations"})
public class ApplicationConfig {

}
