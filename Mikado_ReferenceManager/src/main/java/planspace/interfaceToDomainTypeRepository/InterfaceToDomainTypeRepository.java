package planspace.interfaceToDomainTypeRepository;

import java.util.List;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import planspace.beans.sourceReferences.RequestCreateSourceReference;
import planspace.beans.sourceReferences.RequestFindSourceReferenceByID;
import planspace.beans.sourceReferences.RequestStoreSourceReference;
import planspace.beans.sourceReferences.ResponseSourceReferenceFound;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.compliance.SourceReferenceData;
import planspace.domainTypes.DataPointType;
import planspace.domainTypes.DomainType;
import planspace.domainTypes.ObjectType;
import planspace.domainTypes.TaxonomyNode;
import planspace.interfaceToDomainTypeRepository.requests.RequestCreateChefTreeContext;
import planspace.interfaceToDomainTypeRepository.requests.RequestCreateChefType;
import planspace.interfaceToDomainTypeRepository.requests.RequestCreateObjectType;
import planspace.interfaceToDomainTypeRepository.requests.RequestDeleteChefTreeContextDomain;
import planspace.interfaceToDomainTypeRepository.requests.RequestDeleteChefTypeInDomain;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindChefTreeContextByID;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindChefTypeByID;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindChefTypeByType;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindDataPointTypeByType;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindObjectTypeByType;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindRootChefItemsForDomain;
import planspace.interfaceToDomainTypeRepository.requests.RequestFindTreeContextsForChefType;
import planspace.interfaceToDomainTypeRepository.requests.RequestGetTaxonomyFromObjectTypeCode;
import planspace.interfaceToDomainTypeRepository.requests.RequestStoreChefTreeContext;
import planspace.interfaceToDomainTypeRepository.requests.RequestStoreChefType;
import planspace.interfaceToDomainTypeRepository.requests.RequestUpdateDomainType;
import planspace.interfaceToDomainTypeRepository.requests.RequestUpdateObjectType;
import planspace.interfaceToDomainTypeRepository.responses.ResponseChefTreeContextFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseChefTreeContextsFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseChefTypeDeleted;
import planspace.interfaceToDomainTypeRepository.responses.ResponseChefTypeFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseChefTypesFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseCreateType;
import planspace.interfaceToDomainTypeRepository.responses.ResponseDataPointTypeFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseGetAllDomains;
import planspace.interfaceToDomainTypeRepository.responses.ResponseGetTaxonomy;
import planspace.interfaceToDomainTypeRepository.responses.ResponseObjectTypeFound;
import planspace.interfaceToDomainTypeRepository.responses.ResponseUpdateType;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

/* Deze class is de interface vanuit een Agent met Objective ImpactDetermination agent naar 
 * een externe rule engine 
 * Deze class ontkoppelt het gedrag van de agent van de implementatie de rule engine
 * Hierdoor kan in toekomst andere impact bepalende rule engines worden aangeroepen of
 */

public class InterfaceToDomainTypeRepository {

	private static InterfaceToDomainTypeRepository stdInterface = null;
	private String accessPointUrl = null;
	
	SimpleClientHttpRequestFactory myHTTPfactory;

	public InterfaceToDomainTypeRepository() {
		/* Deze methode maakt een factory aan om REST API's aan te roepen */

		myHTTPfactory = new SimpleClientHttpRequestFactory();
		myHTTPfactory.setConnectTimeout(3000);
		myHTTPfactory.setReadTimeout(3000);

	}

	private String getAccessPointUrl() {
		if (this.accessPointUrl == null) {
			this.accessPointUrl = MikadoSettings.getInstance().getAccessPointURL_DomainTypeRepository();
		}
		return this.accessPointUrl;
		
	}
	public static InterfaceToDomainTypeRepository getInstance() {
		/*
		 * Er is maar 1 aanspreekpunt naar het case mgt systeem nodig voor een agent
		 * Deze class maakt 1 instantie aan indien die nog niet bestaat, anders wordt de
		 * eerder aangemaakte instantie gebruikt.
		 */
		if (stdInterface == null) {

			stdInterface = new InterfaceToDomainTypeRepository();

		}
		return stdInterface;
	}

	public List<DomainType> GetAllDomains() {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */
		List<DomainType> allDomains;

		ResponseGetAllDomains theResponseGetAllDomains;

		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/getAllDomains";

		allDomains = null;

	
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {
			theResponseGetAllDomains = restTemplate.getForObject(uri, ResponseGetAllDomains.class);

			// Verwerken van de call
			if (theResponseGetAllDomains != null) {

				PlanSpaceLogger.getInstance().log_Debug("\n\n\nResponse DomainType Modeller repository: "
						+ theResponseGetAllDomains.getAllDomains().size());

				allDomains = theResponseGetAllDomains.getAllDomains();

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response DomainType Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return allDomains;

	}

	public TaxonomyNode GetTaxonomyForObjectTypeCode(String aDomainCode, String aObjectTypeCode) {

		/* Deze methode verzoekt middels Rest Post om de aanmaak van een nieuwe case */

		TaxonomyNode theTaxonomy;
		RequestGetTaxonomyFromObjectTypeCode theRequest;
		ResponseGetTaxonomy theResponse;

		final String uri = this.getAccessPointUrl() + "/getTaxonomy";


		theTaxonomy = null;

	
		theRequest = new RequestGetTaxonomyFromObjectTypeCode();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setStartFromType(aObjectTypeCode);
		
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseGetTaxonomy.class);
			// Verwerken van de call
			if (theResponse != null) {
			
				theTaxonomy = theResponse.getTheRootNode();

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nNo taxonomy ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theTaxonomy;

	}

	public ObjectType findObjectTypeByTypename(String aDomainCode, String aTypeName) {

		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */
		ObjectType theObjectTypeDefinition;

		ResponseObjectTypeFound theResponse;
		RequestFindObjectTypeByType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findObjectTypeByTypename";

		theObjectTypeDefinition = null;

		// set parameters
		theRequest = new RequestFindObjectTypeByType();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setTypeCode(aTypeName);
		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseObjectTypeFound.class);

			// Verwerken van de call
			if (theResponse != null) {

			
				theObjectTypeDefinition = theResponse.getTheObjectType();

			
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response DomainType Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theObjectTypeDefinition;

	}

	public boolean CreateObjectTypeForDomain(String aDomainCode, ObjectType newObject) {

		boolean ok;

		ResponseCreateType theResponse;
		RequestCreateObjectType theRequest;

		final String uri = this.getAccessPointUrl() + "createObjectTypeInDomain";

		ok = false;

		// set parameters
		theRequest = new RequestCreateObjectType();
		theRequest.setNewObjectType(newObject);


		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {

				ok = true;
				

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError create object in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

	public boolean UpdateObjectTypeForDomain(String aDomainCode, ObjectType newObject) {

		boolean ok;

		ResponseUpdateType theResponse;
		RequestUpdateObjectType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/updateObjectTypeInDomain";

		ok = false;

		// set parameters
		theRequest = new RequestUpdateObjectType();
		theRequest.setTheObjectType(newObject);

		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseUpdateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError update object in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}
	
	
	public boolean updateDomainType(DomainType theDomain) {

		boolean ok;

		ResponseUpdateType theResponse;
		RequestUpdateDomainType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/updateDomainType";

		ok = false;

		// set parameters
		theRequest = new RequestUpdateDomainType();
		theRequest.setTheDomainType(theDomain);

		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseUpdateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError update object in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

	public boolean InsertObjectTypeForDomainUnderParent(String aDomainCode, String analyzedWord,
			ObjectType parentObject, List<String> theChildConceptsToMove, String description) {

		boolean ok;
		ObjectType newConcept;

		ResponseCreateType theResponse;
		RequestCreateObjectType theRequest;

		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/createObjectTypeInDomain";

		// we might fail;
		ok = false;

		// prepare concept
		newConcept = new ObjectType();
		newConcept.setDomainCode(aDomainCode);
		newConcept.setCode(analyzedWord.toUpperCase());
		newConcept.setName(analyzedWord);
		newConcept.setMyParentObjectID(parentObject.get_id());
		if (theChildConceptsToMove != null) {
			newConcept.setCodesOfMyDirectChildren(theChildConceptsToMove);
		}
		newConcept.setDescription(description);
		// set parameters

		theRequest = new RequestCreateObjectType();
		theRequest.setNewObjectType(newConcept);

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {

				ok = true;

			} else {
				PlanSpaceLogger.getInstance()
						.log_Error("\n\n\nError inserting object under parent in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

	public DataPointType findDataPointTypeByTypename(String aDomainCode, String aDataPointTypeName) {

		
		DataPointType theDPDefinition;

		ResponseDataPointTypeFound theResponse;
		RequestFindDataPointTypeByType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findDataPointTypeByTypeName";

	

		theDPDefinition = null;

		// set parameters
		theRequest = new RequestFindDataPointTypeByType();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setTypeName(aDataPointTypeName);
	
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseDataPointTypeFound.class);

			// Verwerken van de call
			if (theResponse != null) {


				theDPDefinition = theResponse.getTheDataPointFound();

		

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response DataPointType Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace DomainType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theDPDefinition;

	}

	public ChefType findChefTypeByID(String aDomainCode, String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */
		ChefType theChefType;

		ResponseChefTypeFound theResponse;
		RequestFindChefTypeByID theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findChefTypeByID";

		

		theChefType = null;

		// set parameters
		theRequest = new RequestFindChefTypeByID();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setTheID(anID);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypeFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theChefType = theResponse.getTheChefType();
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response ChefType repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theChefType;

	}
	
	public List<ChefType> findChefTypeByType(String aDomainCode, String theType, String aTheme) {

		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */
		List<ChefType> theChefTypes;
		String aThemeUsed;

		ResponseChefTypesFound theResponse;
		RequestFindChefTypeByType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findChefTypeByType";

		

		theChefTypes = null;

		// set parameters
		theRequest = new RequestFindChefTypeByType();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setTheType(theType);
		if (aTheme == null) {
			aThemeUsed = "";
		} else
		{
			aThemeUsed = aTheme;
		}
		theRequest.setTheTheme(aThemeUsed);
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypesFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theChefTypes = theResponse.getTheChefTypes();
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response ChefType repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theChefTypes;

	}
	
	public ChefTreeContext findChefTreeContextByID(String aDomainCode, String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */
		ChefTreeContext theChefTreeContext;

		ResponseChefTreeContextFound theResponse;
		RequestFindChefTreeContextByID theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findChefTreeContextByID";

		

		theChefTreeContext= null;

		// set parameters
		theRequest = new RequestFindChefTreeContextByID();
		theRequest.setDomainCode(aDomainCode);
		theRequest.setTheID(anID);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTreeContextFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theChefTreeContext = theResponse.getTheChefTreeContext();
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response ChefTreeContext repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theChefTreeContext;

	}

	public boolean CreateChefTypeForDomain(String aDomainCode, ChefType newChefType) {

		boolean ok;

		ResponseCreateType theResponse;
		RequestCreateChefType theRequest;

		final String uri = this.getAccessPointUrl() + "/createChefType";

		ok = false;

		// set parameters
		theRequest = new RequestCreateChefType();
		theRequest.setNewChefType(newChefType);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {

				ok = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError create chefType in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}
	
	public boolean CreateChefTreeContextForDomain(String aDomainCode, ChefTreeContext newChefTreeContext) {

		boolean ok;

		ResponseCreateType theResponse;
		RequestCreateChefTreeContext theRequest;

		final String uri = this.getAccessPointUrl() + "/createChefTreeContext";

		ok = false;

		// set parameters
		theRequest = new RequestCreateChefTreeContext();
		theRequest.setNewChefTreeContext(newChefTreeContext);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {

				ok = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError create chef tree context in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

	public boolean StoreChefTypeForDomain(String aDomainCode, ChefType aChefType) {
		// create or Update

		boolean ok;

		ResponseCreateType theResponse;
		RequestStoreChefType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/storeChefType";

		ok = false;

		// set parameters
		theRequest = new RequestStoreChefType();
		theRequest.setTheChefType(aChefType);

		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError storing chefType in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}
	
	public boolean StoreChefTreeContextForDomain(String aDomainCode, ChefTreeContext aChefTreeContext) {
		// create or Update

		boolean ok;

		ResponseCreateType theResponse;
		RequestStoreChefTreeContext theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/storeChefTreeContext";

		ok = false;

		// set parameters
		theRequest = new RequestStoreChefTreeContext();
		theRequest.setTheChefTreeContext(aChefTreeContext);

		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError storing chefTree context in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}


	

	public SourceReferenceData findSourceReferenceByID(String anID) {

		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */
		SourceReferenceData theSourceReferenceData;

		ResponseSourceReferenceFound theResponse;
		RequestFindSourceReferenceByID theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findSourceReferenceByID";

		

		theSourceReferenceData = null;

		// set parameters
		theRequest = new RequestFindSourceReferenceByID();

		theRequest.setTheID(anID);

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseSourceReferenceFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theSourceReferenceData = theResponse.getTheSourceReferenceData();
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response source reference repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

		return theSourceReferenceData;

	}

	public boolean CreateSourceReferemceForDomain( SourceReferenceData newRef) {

		boolean ok;

		ResponseCreateType theResponse;
		RequestCreateSourceReference theRequest;

		final String uri = this.getAccessPointUrl() + "/createSourceReference";

		ok = false;

		// set parameters
		theRequest = new RequestCreateSourceReference();

		theRequest.setNewSourceReferenceData(newRef);
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {

				ok = true;

			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError create source reference in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace source reference repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

	public boolean StoreSourceReferenceForDomain(SourceReferenceData aRef) {
		// create or Update

		boolean ok;

		ResponseCreateType theResponse;
		RequestStoreSourceReference theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/storeSourceReference";

		ok = false;

		// set parameters
		theRequest = new RequestStoreSourceReference();
		theRequest.setTheSourceReferenceData(aRef);
		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseCreateType.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError storing chefType in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}


	public List<ChefTreeContext> getAllChefRootsForDomain(String domainCode) {

	   
	    
		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */

	    List<ChefTreeContext> theRoots;
	    ResponseChefTreeContextsFound theResponse;
		RequestFindRootChefItemsForDomain theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findChefTreeRootItems";

		 theRoots = null;


		// set parameters
		theRequest = new RequestFindRootChefItemsForDomain();
		theRequest.setDomainCode(domainCode);
	

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTreeContextsFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theRoots = theResponse.getTheTreeContexts();
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response chef roots repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

	    
		return theRoots;
	}
	
	
	public List<ChefTreeContext> FindAllChefContextRelatedToChefType(String domainCode, String ChefType_ID) {

	   
	    
		/*
		 * Deze methode verzoekt middels Rest Post om een ObjectType op basis van een
		 * type naam
		 */

	    List<ChefTreeContext> theContexts;
	    ResponseChefTreeContextsFound theResponse;
	    RequestFindTreeContextsForChefType theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/findChefTreeContextsForChefType";

		 theContexts = null;


		// set parameters
		theRequest = new RequestFindTreeContextsForChefType();
		theRequest.setDomainCode(domainCode);
		theRequest.setTheID(ChefType_ID);
	

		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTreeContextsFound.class);

			// Verwerken van de call
			if (theResponse != null) {
				theContexts = theResponse.getTheTreeContexts();
				PlanSpaceLogger.getInstance().log_Error("\n\n\nBOE-->  " + theContexts.size());
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nEmpty response chef roots repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace Domain repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}

	    
		return theContexts;
	}


	public boolean deleteChefTypeByID(String domainCode, String chefTypeID) {
		// create or Update

		boolean ok;

		ResponseChefTypeDeleted theResponse;
		RequestDeleteChefTypeInDomain theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/deleteChefTypeByID";

		ok = false;

		// set parameters
		theRequest = new RequestDeleteChefTypeInDomain();
		theRequest.setDomainCode(domainCode);
		theRequest.setTheChefTypeID(chefTypeID);
		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypeDeleted.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError deleting chefType in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}
	
	public boolean deleteTreeContextByID(String domainCode, String chefTreeContextID) {
		// create or Update

		boolean ok;

		ResponseChefTypeDeleted theResponse;
		RequestDeleteChefTreeContextDomain theRequest;
		// EXTRA SERVERS
		final String uri = this.getAccessPointUrl() + "/deleteChefTreeContextByID";

		ok = false;

		// set parameters
		theRequest = new RequestDeleteChefTreeContextDomain();
		theRequest.setDomainCode(domainCode);
		theRequest.setTheChefTreeContextID(chefTreeContextID);
		// PlanSpaceLogger.getInstance().log("SENDING " +theRequest.getDomainCode() + "
		// - " );

		// set up RestCall handler
		RestTemplate restTemplate = new RestTemplate(this.myHTTPfactory);

		// maken the call
		try {

			theResponse = restTemplate.postForObject(uri, theRequest, ResponseChefTypeDeleted.class);

			// Verwerken van de call
			if (theResponse != null) {
				ok = true;
			} else {
				PlanSpaceLogger.getInstance().log_Error("\n\n\nError deleting chefTreeContext in  Modeller repository ");
			}
		} catch (Exception e) {
			PlanSpaceLogger.getInstance()
					.log_Error("External PlanSpace ChefType repository is not available." + e.toString());
			PlanSpaceLogger.getInstance().log_Error("Will try again later");

		}
		return ok;

	}

}
