package application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.frontend.WEB_Session;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

@SpringBootApplication
public class Mikado_ReferenceManager {
//
	public static void main(final String[] args) throws IOException {

		System.out.println("**********************************");
		System.out.println("** PLANSPACE REFERENCE MANAGER ***");
		System.out.println("**********************************");
		System.out.println("");
		PlanSpaceLogger.getInstance().setIdOfMyComponent("Reference Manager");

		PlanSpaceLogger.getInstance().setShowKafkaIO(true);
		PlanSpaceLogger.getInstance().setShowconfigIO(true);

		// StartKafka

		WEB_Session theSession;

		theSession = WEB_Session.getInstance();
		theSession.setPersistanceOn(true);
		if (!MikadoSettings.getInstance().getPersistanceOn()) {
			theSession.setPersistanceOn(false);
		}
		SpringApplication.run(Mikado_ReferenceManager.class, args);

	}

}