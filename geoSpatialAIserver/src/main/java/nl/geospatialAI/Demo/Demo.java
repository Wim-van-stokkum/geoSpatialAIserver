package nl.geospatialAI.Demo;





import nl.geospatialAI.DataAccessHandlers.Planspace_ConfigDB;

import nl.geospatialAI.DataPoints.AllowedValue;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.DataPoints.DataPointType;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Demo {

	public void initDataPointTypes() {
		deleteAllDataPoints();
		this.initDataPointType_BIMFILEURL();
		this.initDataPointType_BUILDINGCATEGORY();
		this.initDataPointType_BUSINESSACTIVITIES();
		this.initDataPointType_CHAMBREOFCOMMERCEDOSSIERNUMBER();
		this.initDataPointType_COMMERCIALUSE();
		this.initDataPointType_DESIGN_HAS_GARDEN();
		this.initDataPointType_MAX_LENGTH_DESTINATIONPANE();
		this.initDataPointType_MAX_LENGTH_GARDEN();
		this.initDataPointType_MAX_LENGTH_OBJECT();
		this.initDataPointType_MAX_WIDTH_DESTINATIONPANE();
        this.initDataPointType_MAX_WIDTH_GARDEN();
        this.initDataPointType_MAX_WIDTH_OBJECT();
        this.initDataPointType_MEASUREDHEIGHT();
        this.initDataPointType_PERC_WATER_PERM_GARDEN();
        this.initDataPointType_PROFESSION_AT_HOME();
        this.initDataPointType_PURPOSE_HM_OBJECT();
        this.initDataPointType_REGISTEREDDUTCHKVK();
        this.initDataPointType_SBI_ORGANISATION();
        this.initDataPointType_SURFACE_CALCULATED_DESTINATIONPANE();
        this.initDataPointType_SURFACE_CALCULATED_GARDEN();
        this.initDataPointType_SURFACE_CALCULATED_OBJECT();
        this.initDataPointType_SURFACE_TILES_GARDEN();
        this.initDataPointType_TOTAL_SURFACE_WATER_NON_PERMABLE();
        this.initDataPointType_WORK_FROM_HOME();
		
	}

	private void deleteAllDataPoints() {

		Planspace_ConfigDB theConfigDB = null;

		theConfigDB = ServerGlobals.getInstance().getPlanSpaceConfigDB();
		theConfigDB.deleteAllDataPointTypes();
	}

	private void initDataPointType_BIMFILEURL() {

		DataPointType newDPtype;
		AllowedValue newValue;


		newDPtype = new DataPointType();
		newDPtype.setDescription("Generated dataPointType for demo purposes");

		newDPtype.setQuestionTextForRole("AANVRAGER", "Hoe heet uw bestand met het ontwerp?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Hoe heet de BIM file?");

		newDPtype.setExplainTextForRole("AANVRAGER", "Als u geen ontwerp bestand heeft, kies dan voor GEEN.");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Raadpleeg de repository met ingediende ontwerp bestanden.");

		newDPtype.setDataPointCategory(DataPoint.DP_category.OTHER.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.FILE_URL.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.BIMFILEURL.toString());

		newValue = new AllowedValue();
		newValue.setCode("NO_BIM");
		newValue.setDisplayText("Gebruik geen BIM file, BIM niet ontvangen");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WONING.BIM");
		newValue.setDisplayText("WONING.BIM");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WONING_TUIN.BIM");
		newValue.setDisplayText("WONING MET TUIN.BIM");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOOR.BIM");
		newValue.setDisplayText("WONING.BIM");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("SCHOOL.BIM");
		newValue.setDisplayText("SCHOOL.BIM");
		newDPtype.addAllowedValue(newValue);

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_PURPOSE_HM_OBJECT() {
		DataPointType newDPtype;
		AllowedValue newValue;
	
		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Voor welk doel wilt u het object gebruiken?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Welke type activiteit wordt gepland for dit object?");

		newDPtype.setExplainTextForRole("AANVRAGER",
				"Dit gegeven hebben we nodig om te controleren of deze bestemming is toegestaan.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Dit gegeven is nodig om de bestemming te valideren tegen beleid bestemmingsplan.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.PURPOSE.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.VALUESELECTION.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.PURPOSE_HM_OBJECT.toString());

		newValue = new AllowedValue();
		newValue.setCode("WONEN");
		newValue.setDisplayText("Gebruikt voor wonen");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("EDUCATIE");
		newValue.setDisplayText("Educatie of scholing");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("PRODUCTIE");
		newValue.setDisplayText("Productie van goederen");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOORACTIVITEITEN");
		newValue.setDisplayText("Gebruikt voor kantoor activiteiten");
		newDPtype.addAllowedValue(newValue);
	}

	private void initDataPointType_BUILDINGCATEGORY() {
		DataPointType newDPtype;
		AllowedValue newValue;


		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat voor type gebouw wilt u plaatsen?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat voor type object is het hoofdgebouw?");

		newDPtype.setExplainTextForRole("AANVRAGER",
				"Wij vragen dit om te bepalen aan welke normen het gebouw en de bestemming dient te voldoen.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Gebruik de classificatie zoals beschreven in definities beleidsdocument.");

		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.BUILDINGCATEGORY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.VALUESELECTION.toString());
		newDPtype.setDefaultValue("WOONGEBOUW");

		newValue = new AllowedValue();
		newValue.setCode("BEDRIJFSGEBOUW");
		newValue.setDisplayText("Bedrijfsgebouw");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("HANDELSGEBOUW");
		newValue.setDisplayText("Handelsgebouw");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("HORECAGEBOUW");
		newValue.setDisplayText("Horeca gebouw");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOORGEBOUW");
		newValue.setDisplayText("Kantoorgebouw");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WOONGEBOUW");
		newValue.setDisplayText("Woongebouw");
		newDPtype.addAllowedValue(newValue);
		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MEASUREDHEIGHT() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Hoe hoog wilt u het gebouw gaan bouwen?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "De hoogte van het object");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Gaat om hoogte vanaf maaiveld tot aan hoogste punt in het ontwerp in meters");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "De hoogte zoals gedefinieerd in de definities van het beleid");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MEASUREDHEIGHT.toString());
		newDPtype.setDefaultValue("0.0");

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_WIDTH_OBJECT() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat wordt de maximale breedte van het gebouw?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de maximale breedte van het object?");
		newDPtype.setExplainTextForRole("AANVRAGER", "De breedte het gebouw in meters");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "De breedte zoals gedefinieerd in de definities van het beleid");

		newDPtype = new DataPointType();

		newDPtype.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_OBJECT.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_LENGTH_OBJECT() {
		DataPointType newDPtype;
		

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat wordt de maximale lengte van het gebouw?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de maximale lengte van het object?");
		newDPtype.setExplainTextForRole("AANVRAGER", "De lengte het gebouw in meters");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "De lengte zoals gedefinieerd in de definities van het beleid");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_OBJECT.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_SBI_ORGANISATION() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is de primaire SBI die de KVK heeft geregistreerd?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de primaire SBI code?");
		newDPtype.setExplainTextForRole("AANVRAGER", "De SBI code zoals geregistreerd in de Kamer van Koophandel");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "De SBI code geregistreerd in Kamer van Koophandel");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.OTHER.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.INTEGERVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.SBI_ORGANISATION.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_SURFACE_CALCULATED_OBJECT() {
		DataPointType newDPtype;


		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat wordt de oppervlakte van de gebouw(en)?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de oppervlakte van hoofd- en bijgebouw?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Het gaat om oppervlakte fundering van hoofd- en bijgebouwen (schuren etc).");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Het gaat om oppervlakte fundering van hoofd- en bijgebouwen (schuren etc).");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_WIDTH_DESTINATIONPANE() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER",
				"Wat is de oppervlakte van het perceel waarop het gebouw wordt geplaatst.");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is oppervlakte van bouwperceel");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Nodig om de verhouding vast te stellen tussen bebouwd en onbebouwd deel.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Nodig om de verhouding vast te stellen tussen bebouwd en onbebouwd deel. Raadpleeg kadaster");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_DESTINATIONPANE.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_LENGTH_DESTINATIONPANE() {
		DataPointType newDPtype;
	
		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER",
				"Wat is de maximale lengte van het perceel waarop het gebouw wordt geplaatst.");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de maximale lengte van het bouwperceel");
		newDPtype.setExplainTextForRole("AANVRAGER", "Nodig om de oppervlakte vast te stellen van het bouwperceel");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Nodig om de oppervlakte vast te stellen van het bouwperceel");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_DESTINATIONPANE.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_SURFACE_CALCULATED_DESTINATIONPANE() {

		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is de oppervlakte van het bouwperceel?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de oppervlakte van het bouwperceel?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Nodig om de verhouding tussen object en bouwperceel vast te stellen");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Oppervlakte bouwperceel is nodig om verhouding bouwing/niet bebouwing vast te stellen");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_COMMERCIALUSE() {
		DataPointType newDPtype;


		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Gaat u werkzaamheden aan huis verrichten?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Voor welk doel wordt het gebouw gebruikt?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Nodig om te beoordelen of deze werkzaamheden passen bij gebouw en omgeving");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Nodig om risico bestemming vast te stellen");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.ACTIVITY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TRUTHVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.COMMERCIALUSE.toString());
		newDPtype.setDefaultValue("false");

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_WORK_FROM_HOME() {
		DataPointType newDPtype;
		
		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Gaat u vanuit huis werken?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Gaat de aanvrager vanuit huis werken?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Er worden eisen gesteld aan het soort beroepen die vanuit huis mogen worden uitgeoefend");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Indien ja, dan wordt getoetst of beroep in categorie B van beroepen valt.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.ACTIVITY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TRUTHVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.WORK_FROM_HOME.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_REGISTEREDDUTCHKVK() {
		DataPointType newDPtype;
		

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Heeft u een inschrijving in de Kamer van Koophandel?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Is de aanvrager ingeschreven bij de Kamer van Koophandel?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Hiermee kunnen we uw openbare gegevens opvragen en zonder extra vragen gegevens verzamelen over uw commerciele activiteiten");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Indien het geval, dan kan deze basisadministratie worden gebruikt.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.ACTIVITY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TRUTHVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.REGISTEREDDUTCHKVK.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_BUSINESSACTIVITIES() {
		DataPointType newDPtype;

		AllowedValue newValue;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is de aard van de activiteiten in uw bedrijf?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de aard van de bedrijfsactiviteiten?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Wij vragen dit om het effect of risico op de wijk vast te kunnen stellen");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Nodig om vast te stellen of deze passen bij beroepen aan huis in categorie B");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.ACTIVITY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TRUTHVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.BUSINESSACTIVITIES.toString());
		newDPtype.setDefaultValue("false");

		newValue = new AllowedValue();
		newValue.setCode("WEBSHOP");
		newValue.setDisplayText("Webshop");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("ADMIN");
		newValue.setDisplayText("Administratie");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("EDUCATION");
		newValue.setDisplayText("Educatie");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("MANUFACT");
		newValue.setDisplayText("Constructie/productie");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("FINANCE");
		newValue.setDisplayText("Financiele diensten");
		newDPtype.addAllowedValue(newValue);
		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_WIDTH_GARDEN() {
		DataPointType newDPtype;
		

		newDPtype = new DataPointType();

		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is de breedte op het breedste punt in de tuin?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de maximale breedte van de tuin?");
		newDPtype.setExplainTextForRole("AANVRAGER", "Dit vragen wij om de oppervlakte van de tuin te berekenen");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Zie definities, nodig om oppervlakte tuin te berekenen");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_MAX_LENGTH_GARDEN() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is de lengte op het breedste punt in de tuin?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is de maximale lengte van de tuin?");
		newDPtype.setExplainTextForRole("AANVRAGER", "Dit vragen wij om de oppervlakte van de tuin te berekenen");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Zie definities, nodig om oppervlakte tuin te berekenen");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_SURFACE_CALCULATED_GARDEN() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Oppervlakte tuin");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Oppervlakte tuin");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Het aandeel dat de tuin inneemt t.o.v oppervlakte van het bouwperceel.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Het aandeel dat de tuin inneemt t.o.v oppervlakte van het bouwperceel.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_CHAMBREOFCOMMERCEDOSSIERNUMBER() {
		DataPointType newDPtype;


		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Onder welke KVK nummer staat u ingeschreven?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Het KVK nummer van de aanvrager");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Dit wordt gebruikt om gegevens uit de basis administratie op te vragen, zodat u deze niet zelf hoeft te verstrekken.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"De basisadministratie KVK zal worden geraadpleegd om feiten te verzamelen");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.OTHER.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TEXT.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_PERC_WATER_PERM_GARDEN() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Vastgesteld percentage watertoelatend gebied");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Het percentage oppervlakte dat water toelatend is.");
		newDPtype.setExplainTextForRole("AANVRAGER", "Belangrijk om risico op wateroverlast vast te stellen.");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Wordt gebruikt om risico op wateroverlast vast te stellen.");
		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.OTHER.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.PERC_WATER_PERM_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_SURFACE_TILES_GARDEN() {
		DataPointType newDPtype;
		

		newDPtype = new DataPointType();

		newDPtype.setQuestionTextForRole("AANVRAGER", "Welk deel van de oppervlakte van de tuin zal worden verhard?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Hoe groot is het verharde deel van de tuin?");
		newDPtype.setExplainTextForRole("AANVRAGER", "Verharding geeft invloed op de watertoelatendheid");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Het verharde deel heeft invloed op de watertoelatendheid.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.SURFACE_TILES_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_DESIGN_HAS_GARDEN() {
		DataPointType newDPtype;
	

		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Is er een voornemen om een tuin aan te leggen?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Bevat het ontwerp een tuin?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Een tuin heeft invloed op de watertoelatendheid van het perceel.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Een tuin heeft invloed op de watertoelatendheid van het perceel.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.TRUTHVALUE.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.DESIGN_HAS_GARDEN.toString());

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_TOTAL_SURFACE_WATER_NON_PERMABLE() {
		DataPointType newDPtype;
		
		newDPtype = new DataPointType();
		newDPtype.setQuestionTextForRole("AANVRAGER", "Totale oppervlakte van het niet waterdoorlatend deel");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Totaal oppervlakte niet waterdoorlatend deel");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Het niet water doorlatend deel van het perceel wordt beoordeeld in het risico op wateroverlast.");
		newDPtype.setExplainTextForRole("BEOORDELAAR", "Dit feit bepaald de kans op potentiele wateroverlast.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.DESIGN.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.NUMBER.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE.toString());
		newDPtype.setAskable(false);

		newDPtype.storeYourSelf();
	}

	private void initDataPointType_PROFESSION_AT_HOME() {
		DataPointType newDPtype;

		AllowedValue newValue;
	

		newDPtype = new DataPointType();

		newDPtype.setQuestionTextForRole("AANVRAGER", "Wat is uw beroep?");
		newDPtype.setQuestionTextForRole("BEOORDELAAR", "Wat is het beroep van de aanvrager?");
		newDPtype.setExplainTextForRole("AANVRAGER",
				"Beroepen aan huis zijn toegestaan, mits zij geen potentieel risico vormen voor de directe leefomgeving.");
		newDPtype.setExplainTextForRole("BEOORDELAAR",
				"Aanvrager dient aan te tonen dat beroep in categorie B van beroepen valt.");

		newDPtype = new DataPointType();
		newDPtype.setDataPointCategory(DataPoint.DP_category.ACTIVITY.toString());
		newDPtype.setDataType(DataPoint.DP_dataType.VALUESELECTION.toString());
		newDPtype.setDataPointType(DataPoint.DP_Type.PROFESSION_AT_HOME.toString());
		newDPtype.setDefaultValue("OTHER");
		newDPtype.setAskable(true);

		newValue = new AllowedValue();
		newValue.setCode("CONSULTANCY");
		newValue.setDisplayText("Consultancy");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("COACHING");
		newValue.setDisplayText("Coaching");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WEBSHOP");
		newValue.setDisplayText("Webshop");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("ACCOUNTANT");
		newValue.setDisplayText("Accountant");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("DENTIST");
		newValue.setDisplayText("Tandarts");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("BARBER");
		newValue.setDisplayText("Kapper");
		newDPtype.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("OTHER");
		newValue.setDisplayText("Een ander beroep dan hierboven vermeld.");
		newDPtype.addAllowedValue(newValue);
		newDPtype.storeYourSelf();
	}

}
