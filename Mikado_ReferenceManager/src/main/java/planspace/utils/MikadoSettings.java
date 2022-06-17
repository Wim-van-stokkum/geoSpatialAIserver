package planspace.utils;

public class MikadoSettings {
	private static MikadoSettings stdSettings;
	private String configFilePath = "config\\mikado.properties";
	private PropertiesConfigurator propertiesConfigurator;

	public static MikadoSettings getInstance() {

		if (stdSettings == null) {

			// Create Loghandler
			System.out.println("CREATING SETTINGS MANAGER");
			stdSettings = new MikadoSettings();

		}
		return stdSettings;

	}

	public MikadoSettings() {
		try {

			propertiesConfigurator = new PropertiesConfigurator(configFilePath).load();

		}

		catch (PropertyException ex) {
			System.out.println("Failed to load properties");
			System.exit(1);
		}

		catch (Exception ex) {
			System.out.println("Error in main application");
			System.exit(1);
		}
	}

	public String getAccessPointURL_CaseManagerRepository() {
		String ap;

		ap = propertiesConfigurator.getProperty("accessPoint.caseManagerRepository");

		return ap;
	}

	public String getAccessPointURL_DomainTypeRepository() {
		String ap;

		ap = propertiesConfigurator.getProperty("accessPoint.domainTypeRepository");

		return ap;
	}

	public String getAccessPointURL_HMIservice() {
		String ap;

		ap = propertiesConfigurator.getProperty("accessPoint.HMIservice");

		return ap;
	}

	public String getAccessPointURL_ruleRepository() {
		String ap;

		ap = propertiesConfigurator.getProperty("accessPoint.ruleRepository");

		return ap;
	}

	public boolean getPersistanceOn() {
		String pers;
		boolean persist;

		persist = true;

		pers = propertiesConfigurator.getProperty("persistance");

		if (pers.toLowerCase().equals("false")) {
			persist = false;
		}
		return persist;
	}

	public String getMongoURI() {
		String ap;

		ap = propertiesConfigurator.getProperty("mongoDB.URI");

		return ap;
	}

	public String getAccessPointURL_ManagedObjectRepository() {
		String ap;

		ap = propertiesConfigurator.getProperty("accessPoint.managedObjectRepository");

		return ap;
	}

	public String getPathToAnnotatedDocuments() {
		String ap;

		ap = propertiesConfigurator.getProperty("library.annotatedDocuments");

		return ap;
	}

	public String getURLToAnnotatedDocuments() {
		String ap;

		ap = propertiesConfigurator.getProperty("library.annotatedDocuments");
		return ap;
		
	}

	public String getPathToBrowser() {
		// TODO Auto-generated method stub browser.pathToExec
		String ap;

		ap = propertiesConfigurator.getProperty("browser.pathToExec");
		return ap;
	
	}

	public String getPathToCSVexport() {
		String ap;

		ap = propertiesConfigurator.getProperty("CSVexport.path");
		return ap;
	}

	public String getPathToJSONexport() {
		String ap;

		ap = propertiesConfigurator.getProperty("JSONexport.path");
		return ap;
	}

}
