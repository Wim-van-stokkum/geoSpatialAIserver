package planspace.domainTypes;

/*Als DomeinBeheerder wil ik binnen mijn domein per UserRole in staat zijn specifieke vraag teksten te maken,
  zodat de communicatie aansluit bij het profiel van de gebruiker

 en RoleBasedQuestionText is de definitie van een vraag text (formulering voor een specifieke UserRole)

Een displayText wordt gebruikt in een vraag naar de gebruiker.
 */

public class RoleBasedQuestionText {

	/*
	 * het gaat om een combinatie tussen de code van een userRole en de bijbehorende
	 * Question Text
	 */

	 String roleType;
	 String questionText;
	 
	 public RoleBasedQuestionText() {
		 
	 }

	public RoleBasedQuestionText(String theRoleType, String theQuestionText) {
		// constructor, legt een userRole/vraagtext combinatie vast.
		this.roleType = theRoleType;
		this.questionText = theQuestionText;
	}

}
