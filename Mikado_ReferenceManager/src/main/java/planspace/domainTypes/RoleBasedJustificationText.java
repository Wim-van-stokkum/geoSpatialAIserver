package planspace.domainTypes;

/*Als DomeinBeheerder wil ik binnen per UserRole in staat zijn specifieke uitleg teksten te maken, zodat de communicatie aansluit bij het profiel van de gebruiker

Een RoleBasedJustificationTextis de definitie van een display text (formulering voor een specifieke UserRole

Een displayText wordt gebruikt zodra de user wil weten waarom een gegeven gevraagd wordt.
*/

public class RoleBasedJustificationText {
  
	/* het gaat om een combinatie tussen de code van een userRole en de bijbehorende Question Text */
public String roleType;
   public String justificationText;
   
   
   public RoleBasedJustificationText(String theRoleType, String theJustificationtext) {
		// constructor, legt een userRole/justificationText combinatie vast.
		this.roleType = theRoleType;
		this.justificationText = theJustificationtext;
	}
}
