package com.shipment.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentApplication.class, args);
	}

}
