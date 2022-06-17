package planspace.domainTypes;

/*Als DomeinBeheerder wil ik binnen mijn domein per UserRole in staat zijn specifieke omschrijvingen te maken, 
 * zodat de communicatie aansluit bij het profiel van de gebruiker

Een RoleBasedDisplayText is de definitie van een display text (geformuleerd voor een specifieke UserRole)

*/

public class RoleBasedDisplayText {

	/*
	 * het gaat om een combinatie tussen de code van een userRole en de bijbehorende
	 * afbeelding/omschrijving Text
	 */
	public String roleType;
	public String displayText;
	
	public RoleBasedDisplayText() {
		
	}

	public RoleBasedDisplayText(String theRoleType, String theDisplayText) {
		// constructor, legt een userRole/omschrijving combinatie vast.
		this.roleType = theRoleType;
		this.displayText = theDisplayText;
	}

}
