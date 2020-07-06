package nl.geospatialAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"nl.geospatialAI"})
public class GeoSpatialAIserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoSpatialAIserverApplication.class, args);
	}

}
