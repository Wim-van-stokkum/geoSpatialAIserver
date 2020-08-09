package nl.geospatialAI.Assessment;

public class PolicyLibrary {
    private int refID = 1000;
 
    
    
	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	public PolicyLibrary() {
		this.setRefID(refID);
		refID = refID  + 1;
		System.out.println("CREATING POLICY LIBRARY [" + this.getRefID() + "]");
		System.out.println("----------------------------------------");
		System.out.println();
	}

	// LIBRARY
	public Proof createProof_UNDER_MAX_HEIGHT( Proof.tProofClassificationType initValue) {
		Proof newProof;
		
		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.UNDER_MAX_HEIGHT);
		newProof.setDisplayName("Bewijs: De hoogte vanaf maaiveld tot nok dient binnen de grenzen te vallen");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
	    newProof.setPolicyReference("Artikel 12.1, bestemmingsplan oost");
	    return  newProof;
	}

	
	public Proof createProof_WITHIN_BOUNDARY_BUILD_SURFACE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Proof subProof; 
		
		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.WITHIN_BOUNDARY_BUILD_SURFACE);
		newProof.setDisplayName("Bewijs: Het gebied op het perceel dat bebouwd is dient binnen beleidsgrenzen te bevinden");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
	    newProof.setPolicyReference("Artikel 12.4, bestemmingsplan oost");
	
	   
	    subProof = this.createProof_SURFACE_FOUNDATION(Proof.tProofClassificationType.POSITIVE);
	    newProof.addChildProof(subProof);
	    
	    subProof = createProof_SURFACE_ADDITIONAL_BUILDINGS(Proof.tProofClassificationType.NEGATIVE);
	    newProof.addChildProof(subProof);
	    
	    return  newProof;
	}
	
	
	//
	

	
	public Proof createProof_SURFACE_FOUNDATION(Proof.tProofClassificationType initValue) {
		Proof newProof;
		
		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.SURFACE_FOUNDATION);
		newProof.setDisplayName("Bewijs: Footprint oppervlakte van de fundering");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setProofResult(Proof.tProofClassificationType.POSITIVE);
	    newProof.setPolicyReference("Artikel 12.5.1, bestemmingsplan oost");
	    return  newProof;
	   
	}
	
	
	public Proof createProof_SURFACE_ADDITIONAL_BUILDINGS(Proof.tProofClassificationType initValue) {
		Proof newProof;
		
		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.SURFACE_ADDITIONAL_BUILDINGS);
		newProof.setDisplayName("Bewijs: Footprint oppervlakte van de bijgebouwen");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setProofResult(Proof.tProofClassificationType.NEGATIVE);
	    newProof.setPolicyReference("Artikel 12.6/1, bestemmingsplan oost");
	    return  newProof;
	   
	}
	
}
