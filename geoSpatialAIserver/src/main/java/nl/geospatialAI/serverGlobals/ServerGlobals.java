package nl.geospatialAI.serverGlobals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import nl.geospatialAI.Agents.CaseManager;
import nl.geospatialAI.Agents.DemoManager;
import nl.geospatialAI.Agents.EventObserver;
import nl.geospatialAI.Agents.ProofManager;
import nl.geospatialAI.Agents.RiskManager;
import nl.geospatialAI.Agents.RiskStatusManager;
import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Case.CasesDAO;
import nl.geospatialAI.DataAccessHandlers.Planspace_ConfigDB;
import nl.geospatialAI.DataAccessHandlers.Planspace_DataAccess;
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
	
	private static DemoManager theDemoManager = null;
	


	private static InternalMessageBroker theInternalMessageBroker = null;
	private PolicyLibrary thePolicyLibrary;
	private CasesDAO caseRegistration;

	private t_LogLevel mylogLevel;
	public String scenario;
	private Planspace_DataAccess thePlanSpaceCluster;
   private Planspace_ConfigDB thePlanSpace_ConfigDB; 

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
			
			stdServ.createDemoManager();
			theDemoManager.listenToExternalEventsOn(theInternalMessageBroker);
			
			stdServ.caseRegistration = CasesDAO.getInstance();

	
			stdServ.log("====================================================");
			stdServ.log(" ");
	
			if (stdServ.createPlanSpaceDBcluster() ) {
				if (stdServ.openPlanSpaceConfigDB().openConfigDB()) {
					
				}
			};

		
		    
			stdServ.log("====================================================");
			stdServ.log(" ");
		
		} 
		
		
	
		stdServ.scenario = "ALL";
			return stdServ;

	

	}






	private Planspace_ConfigDB openPlanSpaceConfigDB() {
		
    	this.thePlanSpace_ConfigDB = this.thePlanSpaceCluster.getTheConfigDB();
    	if (this.thePlanSpace_ConfigDB == null) {
    		if( thePlanSpace_ConfigDB.openConfigDB() ) {
     			this.log("Succes: open planspace config database");
    		}
    		else {
     			this.log("ERROR cannot open planspace config database");
    		}
    	};
    	return 	this.thePlanSpace_ConfigDB;
	}


    public Planspace_ConfigDB  getPlanSpaceConfigDB() {
    	if (this.thePlanSpace_ConfigDB == null) {
    	
    		openPlanSpaceConfigDB();
    	}
    	return this.thePlanSpace_ConfigDB;
    }

	public boolean createPlanSpaceDBcluster() {
    	
    	boolean success;
    	
    	
    	success = false;
		 //Set up connection to MongoDB
		this.log(" ");
		this.log("====================================================");
		this.log("Set connection to MongoDB");
		this.log("====================================================");
	    
		this.thePlanSpaceCluster = new Planspace_DataAccess();
		
		success = this.thePlanSpaceCluster.connectCluster();
		if (success) {
			this.log("Connection planspace_cluster gelukt!");
			this.log("====================================================");
			this.log(" ");
				
		}
		else{
			this.log("ERROR: Connection planspace_cluster mislukt!");
			this.log("====================================================");
			this.log(" ");
				
		}
			
		return success;
    }
    
    public Planspace_DataAccess getPlanSpaceDBcluster() {
    	if (this.thePlanSpaceCluster == null) {
    		createPlanSpaceDBcluster();
    	}
    	return this.thePlanSpaceCluster;
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
	
	private void createDemoManager() {
		theDemoManager = DemoManager.getInstance();
		this.log("Demo manager created: " + theDemoManager.getMyID());
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
