package planspace.domainTypes;

import java.util.UUID;

/* Als DomeinBeheerder wil ik binnen een Domein 1 of meerdere UserRoles kunnen definieren, 
 zodat ik per userRole specifieke vraag en uitleg teksten kan definieren
 
Een UserRole is de definitie van een type gebruiker in mijn domein. 
UserRole hebben behoefte aan op maat gemaakte vraagteksten, uitleg teksten en/of omschrijving van domein elementen

Deze class definieert een UserRole.
*/

/* Domeinen zijn unieke namespaces.
 * Naast een technische identificatie (_id) kent een domein een voor de beheerder betekenisvolle naam en 
 * omschrijving
 * 
 * De code is een leesbare alternatieve unieke Identificatie. 
 * De leesbaarheid maakt debuggen en zoeken door mensen makkelijker dan gebruik van nietszeggende _id.
 * 
 * user rollen hebben alleen betekenis binnen een domein. In een domein kunnen meerdere user rollen voorkomen
 */

public class UserRole {
	String _id;
	String name;
	String code;
	String description;
	String domainCode;

	public UserRole(String aCode, String aName, String aDescription) {
		// Constructor hier wordt een naam aangemaakt met zijn kenmerken. Tevens krijgt
		// elke rol binnen een domein hier
		// een unieke identificatie

		this._id = UUID.randomUUID().toString();
		this.name = aName;
		this.code = aCode;
		this.description = aDescription;

	}

	// Getters en setters met eventuele afwijkende Json namen

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
