package planspace.InterfaceToRuleRepository;

import java.util.List;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import planspace.domainRules.RuleTemplates.ChefTypeCondition;
import planspace.domainRules.RuleTemplates.EventRelevantObjectiveRule;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

/* Deze class is de interface naar de HMI service t.b.v. parsen van NLP
 */

public class InterfaceToRuleRepository {

	private static InterfaceToRuleRepository stdInterface = null;
	private String accessPointUrl = null;
	SimpleClientHttpRequestFactory myHTTPfactory;

	public InterfaceToRuleRepository() {
		/* Deze methode maakt een factory aan om REST API's aan te roepen */

		myHTTPfactory = new SimpleClientHttpRequestFactory();
		myHTTPfactory.setConnectTimeout(3000);
		myHTTPfactory.setReadTimeout(3000);

	}
	
	private String getAccessPointUrl() {
		if (this.accessPointUrl == null) {
			this.accessPointUrl = MikadoSettings.getInstance().getAccessPointURL_ruleRepository();
		}
		return this.accessPointUrl;
		
	}

	public static InterfaceToRuleRepository getInstance() {
		/*
		 * Er is maar 1 aanspreekpunt naar het case mgt systeem nodig voor een agent
		 * Deze class maakt 1 instantie aan indien die nog niet bestaat, anders wordt de
		 * eerder aangemaakte instantie gebruikt.
		 */
		if (stdInterface == null) {

			stdInterface = new InterfaceToRuleRepository();

		}
		return stdInterface;
	}

	public ResponseCreateEventRelevantObjectiveRule storeRuleInRepository(
			EventRelevantObjectiveRule anEventRelevantObjectiveRule) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */

		RequestCreateEventRelevantObjectiveRule theRequest;
		ResponseCreateEventRelevantObjectiveRule theResponse;

		final String uri =  this.getAccessPointUrl() + "/createEventRelevantObjectiveRule";
		PlanSpaceLogger.getInstance().log_Error("\n\n\nSTORING RULE API:\n");

		theResponse = null;

		// set parameters

		theRequest = new RequestCreateEventRelevantObjectiveRule();
		theRequest.setAnEventRelevantObjectiveRule(anEventRelevantObjectiveRule);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateEventRelevantObjectiveRule.class);
			// Verwerken van de call
			if (theResponse != null) {

				PlanSpaceLogger.getInstance()
						.log_LUI("receiving response ruleID: \n\n" + theResponse.getRuleID() + "\n\n");

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API RULE REPOS ERROR] External Rule Repository service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API RULE REPOS ERROR] Will try again later");
		}

		return theResponse;
	}

	public String storeChefTypeConditionInRepository(ChefTypeCondition aChefTypeCondition) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */
		String theID;

		RequestStoreChefTypeCondition theRequest;
		ResponseObjectStored theResponse;

		final String uri =  this.getAccessPointUrl() + "/storeChefTypeCondition";
		// PlanSpaceLogger.getInstance().log_Error("\n\n\nSTORING RULE API:\n");

		theResponse = null;

		theID = null;
		// set parameters

		theRequest = new RequestStoreChefTypeCondition();
		theRequest.setTheChefTypeCondition(aChefTypeCondition);
		System.out.println("\n\nSending :   " + theRequest.toJson());
		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseObjectStored.class);
			// Verwerken van de call
			if (theResponse != null) {

				theID = theResponse.getNewid();
				PlanSpaceLogger.getInstance().log_LUI("receiving response chefTypeCondition ID: \n\n" + theID + "\n\n");

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] no chefTypeCondition stored ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API RULE REPOS ERROR] External Rule Repository service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API RULE REPOS ERROR] Will try again later");
		}

		return theID;
	}

	public ChefTypeCondition findChefTypConditionByConditionID(String domainCode, String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */
		String theID;
		ChefTypeCondition foundCondition;

		RequestFindChefTypeConditionByID theRequest;
		ResponseChefTypeConditionFound theResponse;

		final String uri =  this.getAccessPointUrl() + "/findChefTypConditionByConditionID";
		// PlanSpaceLogger.getInstance().log_Error("\n\n\nSTORING RULE API:\n");

		theResponse = null;
		foundCondition = null;
		theID = null;
		// set parameters

		theRequest = new RequestFindChefTypeConditionByID();
		theRequest.setTheChefTypeConditionID(anID);
		theRequest.setDomainCode(domainCode);

		// System.out.println("\n\nSending : " + theRequest.toJson());
		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypeConditionFound.class);
			// Verwerken van de call
			if (theResponse != null) {

				foundCondition = theResponse.getFoundChefTypeCondition();
				// PlanSpaceLogger.getInstance().log_LUI("receiving response chefTypeCondition
				// ID: \n\n" + theID + "\n\n");

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] no chefTypeCondition stored ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API RULE REPOS ERROR] External Rule Repository service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API RULE REPOS ERROR] Will try again later");
		}

		return foundCondition;
	}

	public List<ChefTypeCondition> findChefTypConditionsForChefObjectID(String domainCode, String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */
		String theID;
		List<ChefTypeCondition> foundConditions;

		RequestFindChefTypeConditionForChefObjectID theRequest;
		ResponseChefTypeConditionsFound theResponse;

		final String uri =  this.getAccessPointUrl() + "/findChefTypConditionsForChefObjectID";
		// PlanSpaceLogger.getInstance().log_Error("\n\n\nSTORING RULE API:\n");

		foundConditions = null;
		theID = null;
		// set parameters

		theRequest = new RequestFindChefTypeConditionForChefObjectID();
		theRequest.setTheChefTypeObjectID(anID);
		theRequest.setDomainCode(domainCode);

		// System.out.println("\n\nSending : " + theRequest.toJson());
		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypeConditionsFound.class);
			// Verwerken van de call
			if (theResponse != null) {

				foundConditions = theResponse.getFoundChefTypeConditions();
				// PlanSpaceLogger.getInstance().log_LUI("receiving response chefTypeCondition
				// ID: \n\n" + theID + "\n\n");

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI NOTIFY] no chefTypeConditions stored ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API RULE REPOS ERROR] External Rule Repository service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API RULE REPOS ERROR] Will try again later");
		}

		return foundConditions;
	}

	public boolean deleteAllChefTypeConditionsForChefTypeObject(String domainCode, String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */
	
		boolean succes;

		RequestDeleteChefTypeConditionForChefObjectID theRequest;
		ResponseObjectDeleted theResponse;

		final String uri =  this.getAccessPointUrl() + "/deleteAllChefTypeConditionsForChefTypeObject";
		// PlanSpaceLogger.getInstance().log_Error("\n\n\nSTORING RULE API:\n");

		// set parameters
		succes = false;
		theRequest = new RequestDeleteChefTypeConditionForChefObjectID();
		theRequest.setTheChefTypeObjectID(anID);
		theRequest.setDomainCode(domainCode);

		// System.out.println("\n\nSending : " + theRequest.toJson());
		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseObjectDeleted.class);
			// Verwerken van de call
			if (theResponse != null) {
				succes = true;
			}

		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API RULE REPOS ERROR] External Rule Repository service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API RULE REPOS ERROR] Will try again later");
		}

		return succes;
	}

}
