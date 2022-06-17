package planspace.InterfaceToHMIservice;

import java.util.List;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import planspace.InterfaceToHMIservice.requests.RequestAnalyzeContextAndMatchConceptsInSentence;
import planspace.InterfaceToHMIservice.requests.RequestAnalyzeContextInSentence;
import planspace.InterfaceToHMIservice.requests.RequestAnalyzeEventConditionInSentence;
import planspace.InterfaceToHMIservice.requests.RequestAnalyzeYesNoAnswerInSentence;
import planspace.InterfaceToHMIservice.requests.RequestConceptDescriptionInSentence;
import planspace.InterfaceToHMIservice.requests.RequestDomainConceptsInSentence;
import planspace.InterfaceToHMIservice.requests.RequestPropertyOfConceptInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseAnalyzeConceptDescriptionInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseAnalyzeConditionInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseAnalyzeContextInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseAnalyzePropertyOfConceptInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseAnalyzeYesNoAnswerInSentence;
import planspace.InterfaceToHMIservice.responses.ResponseDomainConceptsInSentence;
import planspace.InterfaceToHMIservice.responses.ResponsePartWordClassificationContext;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_YesNoUnknownNVT;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;
import speechInterface.ParsedConceptProperty;
import speechInterface.WordClassification;

/* Deze class is de interface naar de HMI service t.b.v. parsen van NLP
 */

public class InterfaceToHMIservice {

	private static InterfaceToHMIservice stdInterface = null;
	private String accessPointUrl = null;
	
	SimpleClientHttpRequestFactory myHTTPfactory;

	public InterfaceToHMIservice() {
		/* Deze methode maakt een factory aan om REST API's aan te roepen */

		myHTTPfactory = new SimpleClientHttpRequestFactory();
		myHTTPfactory.setConnectTimeout(3000);
		myHTTPfactory.setReadTimeout(3000);

	}
	
	private String getAccessPointUrl() {
		if (this.accessPointUrl == null) {
			this.accessPointUrl = MikadoSettings.getInstance().getAccessPointURL_HMIservice();
		}
		return this.accessPointUrl;
		
	}

	public static InterfaceToHMIservice getInstance() {
		/*
		 * Er is maar 1 aanspreekpunt naar het case mgt systeem nodig voor een agent
		 * Deze class maakt 1 instantie aan indien die nog niet bestaat, anders wordt de
		 * eerder aangemaakte instantie gebruikt.
		 */
		if (stdInterface == null) {

			stdInterface = new InterfaceToHMIservice();

		}
		return stdInterface;
	}

	public ResponsePartWordClassificationContext AnalyseContextInSentence(String aDomainCode, String aSentenceToAnalyze,
			String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin zonder dat deze wordt gematched met een taxonomy
		 */

		RequestAnalyzeContextInSentence theRequest;
		ResponseAnalyzeContextInSentence theResponse;
		ResponsePartWordClassificationContext theResult;

		final String uri = this.getAccessPointUrl() + "/analyzeContextInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResult = null;

		// set parameters

		theRequest = new RequestAnalyzeContextInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseAnalyzeContextInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {

				theResult = theResponse.getAnalysisResult();
				if (theResult != null) {
					PlanSpaceLogger.getInstance().log_LUI("receiving response: \n\n" + theResult.toJson() + "\n\n");

				} else {
					PlanSpaceLogger.getInstance().log_LUI("no response");
				}

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResult;
	}

	public ResponsePartWordClassificationContext analyzeContextAndMatchConceptsInSentence(String aDomainCode,
			String aSentenceToAnalyze, String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van de context van een
		 * zin waarbij deze vervolgens wordt gematched met de taxonomy van het opgegeven
		 * domein
		 */

		RequestAnalyzeContextAndMatchConceptsInSentence theRequest;
		ResponseAnalyzeContextInSentence theResponse;
		ResponsePartWordClassificationContext theResult;

		final String uri = this.getAccessPointUrl() + "/analyzeContextAndMatchConceptsInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResult = null;

		// set parameters

		theRequest = new RequestAnalyzeContextAndMatchConceptsInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);
		theRequest.setDomainCode(aDomainCode);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseAnalyzeContextInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {

				theResult = theResponse.getAnalysisResult();

				if (theResult != null) {
					PlanSpaceLogger.getInstance().log_LUI("receiving response: \n\n" + theResult.toJson() + "\n\n");

				} else {
					PlanSpaceLogger.getInstance().log_LUI("no response");
				}

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResult;
	}

	public ResponseAnalyzeConditionInSentence analyzeEventConditionInSentence(String aDomainCode,
			String aSentenceToAnalyze, String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van een event conditie
		 * van een zin waarbij deze vervolgens wordt gematched met de taxonomy van het
		 * opgegeven domein
		 */

		RequestAnalyzeEventConditionInSentence theRequest;
		ResponseAnalyzeConditionInSentence theResponse;
		ResponsePartWordClassificationContext theResult;

		final String uri = this.getAccessPointUrl() + "/analyzeEventConditionInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResponse = null;

		// set parameters

		theRequest = new RequestAnalyzeEventConditionInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setDomainCode(aDomainCode);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseAnalyzeConditionInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {
				PlanSpaceLogger.getInstance().log_LUI("\n[CONDITION IN SENTENCE] receiving response: \n\n" + theResponse.toJson() + "\n\n");
				theResult = theResponse.getAnalysisResult();
				if (theResult != null) {
					PlanSpaceLogger.getInstance().log_LUI("\n[CONDITION IN SENTENCE] receiving result: \n\n" + theResult.toJson() + "\n\n");

				} else {
					PlanSpaceLogger.getInstance().log_LUI("[CONDITION IN SENTENCE] no response");
				}

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResponse;
	}

	public t_YesNoUnknownNVT analyzeYesNoAnswerInSentence(String aDomainCode, String aSentenceToAnalyze, String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse een ja / nee antwoord
		 * in een zin waarbij deze vervolgens wordt gematched met de taxonomy van het
		 * opgegeven domein
		 */

		RequestAnalyzeYesNoAnswerInSentence theRequest;
		ResponseAnalyzeYesNoAnswerInSentence theResponse;
		t_YesNoUnknownNVT theResult;

		final String uri = this.getAccessPointUrl() + "/analyzeYesNoAnswerInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResult = t_YesNoUnknownNVT.na;

		// set parameters

		theRequest = new RequestAnalyzeYesNoAnswerInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseAnalyzeYesNoAnswerInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {

				if (theResponse.getYesOrNo() !=  true) {
					theResult = t_YesNoUnknownNVT.na;
				}
				else
					if (theResponse.getIsPositive() == true) {
						theResult = t_YesNoUnknownNVT.yes;
					} else if (theResponse.getIsPositive() == false) {
						theResult = t_YesNoUnknownNVT.no;
					} else {
						theResult = t_YesNoUnknownNVT.unknown;
					}
						
						
				
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResult;
	}

	public List<WordClassification> analyseDomainConceptsInSentence(String aDomainCode, String aSentenceToAnalyze,
			String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse een of meerdere
		 * domeinconcepten te ontdekken in een zin waarbij deze vervolgens wordt
		 * gematched met de taxonomy van het opgegeven domein
		 */

		RequestDomainConceptsInSentence theRequest;
		ResponseDomainConceptsInSentence theResponse;
		List<WordClassification> theResult;
		int i;

		final String uri = this.getAccessPointUrl() + "/analyzeDomainConceptsInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResult = null;

		// set parameters

		theRequest = new RequestDomainConceptsInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);
		theRequest.setDomainCode(aDomainCode);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseDomainConceptsInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {

				theResult = theResponse.getConceptPropertiesFound();
				if (theResult != null) {
					for (i = 0 ; i <theResult.size(); i++ ) {
						PlanSpaceLogger.getInstance().log_LUI("receiving response:" + theResult.get(i).getAnalyzedWord());
					}
				} else {
					PlanSpaceLogger.getInstance().log_LUI("no response");
				}
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResult;
	}

	public List<WordClassification> analyseConceptDescriptionInSentence(String aDomainCode, String aSentenceToAnalyze,
			String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse de omschrijving van een
		 * domein concept in een zin waarbij deze vervolgens wordt gematched met de
		 * taxonomy van het opgegeven domein
		 */

		RequestConceptDescriptionInSentence theRequest;
		ResponseAnalyzeConceptDescriptionInSentence theResponse;
		List<WordClassification> theResult;
		
		int i;

		final String uri = this.getAccessPointUrl() + "/analyzeConceptDescriptionInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResult = null;

		// set parameters

		theRequest = new RequestConceptDescriptionInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);
		theRequest.setDomainCode(aDomainCode);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest,
					ResponseAnalyzeConceptDescriptionInSentence.class);
			// Verwerken van de call
			if (theResponse != null) {

				theResult = theResponse.getConceptPropertiesFound();
				if (theResult != null) {
					for (i = 0 ; i <theResult.size(); i++ ) {
						PlanSpaceLogger.getInstance().log_LUI("receiving response:" + theResult.get(i).getAnalyzedWord());
					}
				

				} else {
					PlanSpaceLogger.getInstance().log_LUI("no response");
				}
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No sentence analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theResult;
	}
	
	
	public 		List <ParsedConceptProperty> analyzePropertiesOfConceptInSentence(String aDomainCode,
			String aSentenceToAnalyze, String language) {

		/*
		 * Deze methode verzoekt middels Rest Post om de analyse van een event conditie
		 * van een zin waarbij deze vervolgens wordt gematched met de taxonomy van het
		 * opgegeven domein
		 */

		RequestPropertyOfConceptInSentence theRequest;
		ResponseAnalyzePropertyOfConceptInSentence  theResponse;
		List <ParsedConceptProperty> theConceptsDetected;

		final String uri = this.getAccessPointUrl() + "/analyzePropertyOfConceptInSentence";
		PlanSpaceLogger.getInstance()
				.log_Error("\n\n\nSENDING REQUEST SENTENCE ANALISIS FOR óPROPERTIES:\n" + aSentenceToAnalyze + "\n\n\n");

		// we might fail;
		theResponse = null;
		theConceptsDetected = null;

		// set parameters

		theRequest = new RequestPropertyOfConceptInSentence();
		theRequest.setSentence(aSentenceToAnalyze);
		theRequest.setLanguage(language);
		theRequest.setDomainCode(aDomainCode);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseAnalyzePropertyOfConceptInSentence.class);
			// Verwerken van de call
			PlanSpaceLogger.getInstance().log_LUI("\n\n[API RESPONSE] "+  theResponse.toJson() + "\n\n"  );
			if (theResponse != null) {
				theConceptsDetected = theResponse.getTheParsedConceptProperties();
				
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\n[API HMI ERROR] No property analysis result ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("[API HMI ERROR] External HMI service not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("[API HMI ERROR] Will try again later");
		}

		return theConceptsDetected;
	}


	
}
