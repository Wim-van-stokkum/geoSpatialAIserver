package planspace.interfaceToCaseManagerRepository;

import java.util.List;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import application.frontend.WEB_Session;
import planspace.caseModel.Case;
import planspace.caseModel.CaseType;
import planspace.interfaceToCaseManagerRepository.requests.RequestAddCaseType;
import planspace.interfaceToCaseManagerRepository.requests.RequestDeleteCaseType;
import planspace.interfaceToCaseManagerRepository.requests.RequestFindCaseTypesInDomain;
import planspace.interfaceToCaseManagerRepository.requests.RequestFindCasesInDomain;
import planspace.interfaceToCaseManagerRepository.requests.RequestFindCasetypeByID;
import planspace.interfaceToCaseManagerRepository.requests.RequestNewCase;
import planspace.interfaceToCaseManagerRepository.requests.RequestSyncCase;
import planspace.interfaceToCaseManagerRepository.requests.RequestUpdateCaseType;
import planspace.interfaceToCaseManagerRepository.responses.ResponseCaseTypeFound;
import planspace.interfaceToCaseManagerRepository.responses.ResponseCreateCaseType;
import planspace.interfaceToCaseManagerRepository.responses.ResponseCreateType;
import planspace.interfaceToCaseManagerRepository.responses.ResponseDeleteAllinDomain;
import planspace.interfaceToCaseManagerRepository.responses.ResponseFoundCaseTypes;
import planspace.interfaceToCaseManagerRepository.responses.ResponseFoundCases;
import planspace.interfaceToCaseManagerRepository.responses.ResponseSyncCase;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

public class InterfaceToCaseManagerRepository {
	private static InterfaceToCaseManagerRepository stdInterface = null;
	private String accessPointUrl = null;

	SimpleClientHttpRequestFactory myHTTPfactory;
	
	private String getAccessPointUrl() {
		if (this.accessPointUrl == null) {
			this.accessPointUrl = MikadoSettings.getInstance().getAccessPointURL_CaseManagerRepository();
		}
		return this.accessPointUrl;
		
	}

	public InterfaceToCaseManagerRepository() {
		/* Deze methode maakt een factory aan om REST API's aan te roepen */

		myHTTPfactory = new SimpleClientHttpRequestFactory();
		myHTTPfactory.setConnectTimeout(3000);
		myHTTPfactory.setReadTimeout(3000);

	}

	public static InterfaceToCaseManagerRepository getInstance() {
		/*
		 * Er is maar 1 aanspreekpunt naar het case mgt systeem nodig voor een agent
		 * Deze class maakt 1 instantie aan indien die nog niet bestaat, anders wordt de
		 * eerder aangemaakte instantie gebruikt.
		 */
		if (stdInterface == null) {

			stdInterface = new InterfaceToCaseManagerRepository();

		}
		return stdInterface;
	}

	public List<CaseType> findCaseTypes() {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		List<CaseType> allCaseTypes;
		RequestFindCaseTypesInDomain theRequest;
		String aDomainCode;
		ResponseFoundCaseTypes theResponseFoundCaseTypes;

		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findCaseTypes";

		allCaseTypes = null;
		aDomainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		theRequest = new RequestFindCaseTypesInDomain();
		theRequest.setDomainCode(aDomainCode);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseFoundCaseTypes = restTemplate.postForObject(uri, theRequest, ResponseFoundCaseTypes.class);

			// Verwerken van de call
			if (theResponseFoundCaseTypes != null) {
				allCaseTypes = theResponseFoundCaseTypes.getFoundCaseTypes();

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return allCaseTypes;

	}

	public boolean updateCaseType(CaseType theCaseType) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		boolean succes;

		RequestUpdateCaseType theRequest;
		ResponseCreateCaseType theResponseCreateCaseType;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/updateCaseType";

		succes = false;
		
		theRequest = new RequestUpdateCaseType();
		theRequest.setTheCaseType(theCaseType);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseCreateCaseType = restTemplate.postForObject(uri, theRequest, ResponseCreateCaseType.class);

			// Verwerken van de call
			if (theResponseCreateCaseType != null) {
				succes = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return succes;

	}

	
	
	public boolean addCaseType(CaseType theCaseType) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		boolean succes;

		RequestAddCaseType theRequest;
		ResponseCreateCaseType theResponseCreateCaseType;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/addCaseType";

		succes = false;
		
		theRequest = new RequestAddCaseType();
		theRequest.setTheCaseType(theCaseType);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseCreateCaseType = restTemplate.postForObject(uri, theRequest, ResponseCreateCaseType.class);

			// Verwerken van de call
			if (theResponseCreateCaseType != null) {
				succes = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return succes;

	}
	
	

	public boolean deleteCaseType(CaseType theCaseType) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		boolean succes;

		RequestDeleteCaseType theRequest;
		ResponseDeleteAllinDomain theResponseCreateCaseType;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/deleteCaseType";

		succes = false;
		
		theRequest = new RequestDeleteCaseType();
		theRequest.setTheCaseType(theCaseType);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseCreateCaseType = restTemplate.postForObject(uri, theRequest, ResponseDeleteAllinDomain.class);

			// Verwerken van de call
			if (theResponseCreateCaseType != null) {
				succes = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return succes;

	}
	
	
	public List<Case> findCases(String caseTypeID) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		List<Case> allCases;
		RequestFindCasesInDomain theRequest;
		String aDomainCode;
		ResponseFoundCases theResponseFoundCases;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/findCasesInDomain";

		allCases = null;
		aDomainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		theRequest = new RequestFindCasesInDomain();
		theRequest.setDomainCode(aDomainCode);
		if (caseTypeID != null) {
			theRequest.setCaseTypeID(caseTypeID);	
		}

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseFoundCases = restTemplate.postForObject(uri, theRequest, ResponseFoundCases.class);

			// Verwerken van de call
			if (theResponseFoundCases != null) {
				allCases = theResponseFoundCases.getFoundCases();

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return allCases;

	}

	public boolean createCase(Case newCase) {
	     

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		boolean succes;

		RequestNewCase theRequest;
		ResponseCreateType theResponse;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/newCase";

		succes = false;
		
		theRequest = new RequestNewCase();
		theRequest.setTheCase(newCase);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				succes = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return succes;

	}
	
	public boolean updateCase(Case theCase) {
	     

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		boolean succes;

		RequestSyncCase theRequest;
		ResponseSyncCase theResponse;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/synchroniseCase";

		succes = false;
		
		theRequest = new RequestSyncCase();
		theRequest.setTheCase(theCase);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseSyncCase.class);

			// Verwerken van de call
			if (theResponse != null) {
				succes = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return succes;

	}

	public CaseType findCaseTypeByID(String caseTypeID) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		CaseType aCaseType;
		RequestFindCasetypeByID theRequest;
		String aDomainCode;
		ResponseCaseTypeFound theResponseCaseTypeFound;

		// EXTRA SERVERS
		final String uri =  this.getAccessPointUrl() + "/findCaseTypeByID";

		aCaseType = null;
		aDomainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		theRequest = new RequestFindCasetypeByID();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setCaseTypeID(caseTypeID);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponseCaseTypeFound = restTemplate.postForObject(uri, theRequest, ResponseCaseTypeFound.class);

			// Verwerken van de call
			if (theResponseCaseTypeFound != null) {
				aCaseType = theResponseCaseTypeFound.getTheCaseType();

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response CaseManager repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace CaseManager repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return aCaseType;

	}
	
}
