package planspace.domainTypes;


/* Als DomeinBeheerder wil ik binnen mijn domein een toegestane waarde kunnen vastleggen voor een DataPointType
 zodat ik kan sturen op de antwoorden die ik terug kan krijgen

Een AllowedValue is de definitie van een toegestane waarde die een 
DataPointType (van type ValueSelection) mag aannemen.

Wordt gebruikt bij enumeraties. Bijvoorbeeld DataPointType "risicoclass" mag AllowedValues hebben:
laag, midden, hoog, onacceptabel.

*/


public class AllowedValue {
	/* Elke allowed value heeft een (technische) code. Deze is uniek binnen een DataPointType van type ValueSelection
	 * Daarnaast is er een display text met een voor de gebruiker betekenisvolle omschrijving van de mogelijk waarde
	 * 
	 * AllowedValues worden binnen een domain beheerd
	 */
	
	String code;

	String displayText;
	
	// getters en setters

	public AllowedValue() {
		
	}
	


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}


}
