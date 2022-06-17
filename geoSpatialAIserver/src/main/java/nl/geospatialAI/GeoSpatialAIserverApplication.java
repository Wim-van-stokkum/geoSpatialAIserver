package nl.geospatialAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = {"nl.geospatialAI"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class GeoSpatialAIserverApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(GeoSpatialAIserverApplication.class, args);
	}

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(GeoSpatialAIserverApplication.class);
	 }

	
}
