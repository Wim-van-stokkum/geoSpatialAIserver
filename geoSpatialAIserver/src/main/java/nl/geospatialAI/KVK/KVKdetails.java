package nl.geospatialAI.KVK;

import nl.geospatialAI.serverGlobals.ServerGlobals;

public class KVKdetails {
	public enum tLegalForm {
		BV, NV, EENMANSZAAK, VOF, VERENINGING, STICHTING, VOF_COMMANDAIRE, OTHER

	}

	private String dossierNumber;
	private int codeSBI;
	private String SBIname;
	private tLegalForm legalForm;
	private String companyName;
	private boolean readKVK = false;

	public String getDossierNumber() {
		return dossierNumber;
	}

	public void setDossierNumber(String dossierNumber) {
		this.dossierNumber = dossierNumber;
	}


	public String getSBIname() {
		return SBIname;
	}

	public void setCodeSBI(String aSBIname) {
		this.SBIname = aSBIname;
	}
	public int getCodeSBI() {
		return codeSBI;
	}

	public void setCodeSBI(int codeSBI) {
		this.codeSBI = codeSBI;
	}

	public tLegalForm getLegalForm() {
		return legalForm;
	}

	public void setLegalForm(tLegalForm legalForm) {
		this.legalForm = legalForm;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void readKVK(String aKVKnummer) {
		// TODO Auto-generated method stub
		  ServerGlobals.getInstance().log("READING KVK: " + aKVKnummer);
		  
		  // TO DO COMPANY INFO CALL
		  this.setReadKVK(false);
		  if (aKVKnummer.equals("65890493")) {
			  this.setDossierNumber(aKVKnummer);
			  this.companyName = "inIT4real";
			  this.legalForm = KVKdetails.tLegalForm.EENMANSZAAK;
			  this.codeSBI = 70221;
			  this.SBIname = "Organisatie-adviesbureaus";
			  this.setReadKVK(true);
		  }
		  else
		  {
			  this.companyName = "Stichting Bier Bands & Bunkeren";
			  this.setDossierNumber(aKVKnummer);
			  this.legalForm = KVKdetails.tLegalForm.STICHTING;
			  this.codeSBI = 110500;
			  this.SBIname = "Productie van bier";
			  this.setReadKVK(true);
		  }
			  
		
	}

	public boolean isReadKVK() {
		return readKVK;
	}

	public void setReadKVK(boolean readKVK) {
		this.readKVK = readKVK;
	}

}
