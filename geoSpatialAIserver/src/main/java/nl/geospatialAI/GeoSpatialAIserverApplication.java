package nl.geospatialAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication(scanBasePackages = {"nl.geospatialAI"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class GeoSpatialAIserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoSpatialAIserverApplication.class, args);
	}

}
