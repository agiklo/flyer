package pl.matcodem.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ReservationServiceApplication::main).with(TestReservationServiceApplication.class).run(args);
	}

}
