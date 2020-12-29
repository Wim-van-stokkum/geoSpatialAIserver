package nl.geospatialAI.serverGlobals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import nl.geospatialAI.Agents.CaseManager;
import nl.geospatialAI.Agents.EventObserver;
import nl.geospatialAI.Agents.ProofManager;
import nl.geospatialAI.Agents.RiskManager;
import nl.geospatialAI.Agents.RiskStatusManager;
import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Case.CasesDAO;
import nl.geospatialAI.MessageBrokers.InternalMessageBroker;

public class ServerGlobals {
	public enum t_LogLevel {
		HIGH_DETAIL
	}

	private static ServerGlobals stdServ = null;
	private static EventObserver theObserver = null;
	private static CaseManager theCaseManager = null;
	private static RiskManager theRiskManager = null;
	private static ProofManager theProofManager = null;
	private static RiskStatusManager theRiskStatusManager = null;
	
	


	private static InternalMessageBroker theInternalMessageBroker = null;
	private PolicyLibrary thePolicyLibrary;
	private CasesDAO caseRegistration;

	private t_LogLevel mylogLevel;
	public String scenario;

	public static ServerGlobals getInstance() {

		if (stdServ == null) {

			   // Create Loghandler
			stdServ = new ServerGlobals();
			stdServ.setLogLevel(ServerGlobals.t_LogLevel.HIGH_DETAIL);

			
		
			
			 //Create team of agents
			stdServ.log(" ");
			stdServ.log("====================================================");
			stdServ.log("Creating team of internal agents");
			stdServ.log("====================================================");
			
			// Create service bus
			stdServ.createInternalMessageBroker();
			
			stdServ.createObserver();
		    theObserver.listenToExternalEventsOn(theInternalMessageBroker);
			
			stdServ.createCaseManager();
			theCaseManager.listenToInternalEventsOn(theInternalMessageBroker);
			
			stdServ.createRiskManager();
			theRiskManager.listenToInternalEventsOn(theInternalMessageBroker);
			
			stdServ.createRiskStatusManager();
			theRiskStatusManager.listenToInternalEventsOn(theInternalMessageBroker);
			
			stdServ.createProofManager();
			theProofManager.listenToInternalEventsOn(theInternalMessageBroker);
			
			stdServ.caseRegistration = CasesDAO.getInstance();

	
			stdServ.log("====================================================");
			stdServ.log(" ");
	
			
			 //Set up connection to MongoDB
			stdServ.log(" ");
			stdServ.log("====================================================");
			stdServ.log("Set connection to MongoDB");
			stdServ.log("====================================================");
		    
			if ( stdServ.connect("planspace_cases")) {
					stdServ.log("Connection planspace_cases gelukt!");
					stdServ.log("====================================================");
					stdServ.log(" ");
		} else
		{
			stdServ.log("Connection planspace_cases mislukt");
			stdServ.log("====================================================");
			stdServ.log(" ");
			
			
			
		
			

		
		}
		}
		
	
		stdServ.scenario = "ALL";
			return stdServ;

	

	}

	
	public  boolean connect(String dbName) {
		boolean succes;
		

		 MongoClient client;
		 MongoDatabase database;
		 MongoCollection riskTypeCollection;

		 MongoIterable<String> theCollections;
		 MongoCursor<String> it;
		 ArrayList<String> collectionNames;

		 MongoClientURI connectURI;

		
		connectURI = new MongoClientURI(
				"mongodb://GeoSpatialUser:Ikga2GeoSpatialDB20@cluster0-shard-00-00.4wc3r.mongodb.net:27017,cluster0-shard-00-01.4wc3r.mongodb.net:27017,cluster0-shard-00-02.4wc3r.mongodb.net:27017/"
						+ dbName
						+ "?ssl=true&replicaSet=atlas-11s8w3-shard-0&authSource=admin&retryWrites=true&w=majority");

		succes = false;

		client = new MongoClient(connectURI);
		if (client == null) {
			System.out.println("Kan Mongo client niet aanmaken");

		} else {

			database = client.getDatabase(dbName);
			if (database == null) {
				System.out.println("Database " + dbName + " kon niet worden gevonden");
			} else {
				succes = true;
			}

		}
		return succes;
	}

	
	private void createProofManager() {
		theProofManager = ProofManager.getInstance();
		this.log("Proof manager created: " + theProofManager.getMyID());
		
	}

	private void createRiskStatusManager() {
		theRiskStatusManager = RiskStatusManager.getInstance();
		this.log("Risk status manager created: " + theRiskManager.getMyID());
		
	}

	private void createRiskManager() {
		theRiskManager = RiskManager.getInstance();
		this.log("Risk manager created: " + theRiskManager.getMyID());
		
		
	}

	private void createCaseManager() {
		theCaseManager = CaseManager.getInstance();
		this.log("Case manager created: " + theCaseManager.getMyID());
		
	}

	private void createObserver() {
		theObserver = EventObserver.getInstance();
		this.log("Observer created: " + theObserver.getMyID());
	}

	private void createInternalMessageBroker() {
		theInternalMessageBroker = InternalMessageBroker.getInstance();
		this.log("Internal message broker created: " + theInternalMessageBroker.getMyID());
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public t_LogLevel getLogLevel() {
		return mylogLevel;
	}

	public void setLogLevel(t_LogLevel mylogLevel) {
		this.mylogLevel = mylogLevel;
	}

	public CasesDAO getCaseRegistration() {
		return caseRegistration;
	}

	public EventObserver getTheObserver() {
		return theObserver;
	}

	public InternalMessageBroker getTheInternalMessageBroker() {
		return theInternalMessageBroker;
	}

	public PolicyLibrary getPolicyLibrary() {

		if (thePolicyLibrary == null) {

			thePolicyLibrary = new PolicyLibrary();

			return thePolicyLibrary;

		}

		else {

			return thePolicyLibrary;

		}

	}

	public void log(String theMessage) {
		if (this.getLogLevel() == ServerGlobals.t_LogLevel.HIGH_DETAIL) {
			System.out.println(theMessage);
		}
	}

	public void setScenario(String scenario2) {
		// TODO Auto-generated method stub
		this.scenario = scenario2;
	}
	
	

}
