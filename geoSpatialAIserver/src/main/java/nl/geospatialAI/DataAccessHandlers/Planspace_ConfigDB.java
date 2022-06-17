package nl.geospatialAI.DataAccessHandlers;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import nl.geospatialAI.DataPoints.DataPointType;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Planspace_ConfigDB {
	private MongoClient planspace_DBclient = null;
	private MongoDatabase planspace_ConfigDatabase = null;
	private String planspace_ConfigDBname = "planspace_config";


	
	
	// collections

	private Planspace_Datapoint_ConfigDB theDataPointTypeConfiguration;

	public Planspace_ConfigDB(MongoClient a_planspace_DBclient) {
		this.planspace_DBclient = a_planspace_DBclient;
	}

	public MongoClient getPlanspace_DBclient() {
		return planspace_DBclient;
	}

	public void setPlanspace_DBclient(MongoClient planspace_DBclient) {
		this.planspace_DBclient = planspace_DBclient;
	}

	public String getPlanspace_ConfigDBname() {
		return planspace_ConfigDBname;
	}

	public MongoDatabase getPlanspace_ConfigDatabase() {
		if (this.planspace_ConfigDatabase == null) {
			this.openConfigDB();
		}
		return planspace_ConfigDatabase;
	}

	public boolean openConfigDB() {
		boolean succes;

		succes = false;
		if (this.planspace_ConfigDatabase == null) {
			this.planspace_ConfigDatabase = this.planspace_DBclient.getDatabase(this.planspace_ConfigDBname);
			if (this.planspace_ConfigDatabase != null) {

				succes = openDataPointTypeConfig();
				if (succes) {
					ServerGlobals.getInstance().log("SUCCES opening one or more collections in config database");
				} else {
					ServerGlobals.getInstance().log("ERROR opening one or more collections in config database");
				}
			}
		}
		return succes;

	}

	private boolean openDataPointTypeConfig() {
		boolean succes;

		succes = false;
		if (this.theDataPointTypeConfiguration == null) {
			this.theDataPointTypeConfiguration = new Planspace_Datapoint_ConfigDB(this.planspace_DBclient,
					this.planspace_ConfigDatabase);
			if (this.theDataPointTypeConfiguration != null) {

				succes = true;
			}
		}
		return succes;

	}

	public void storeDataPointType(DataPointType aDataPointType) {

		if (this.theDataPointTypeConfiguration == null) {

			openDataPointTypeConfig();
		}
		if (this.theDataPointTypeConfiguration != null) {

			theDataPointTypeConfiguration.storeDataPointType(aDataPointType);
		} else {
			ServerGlobals.getInstance().log("ERROR No DataPointTypeConfiguration available");
		}
	}

	
	public boolean deleteAllDataPointTypes() {
		boolean succes;

		succes = false;
		if (this.theDataPointTypeConfiguration != null) {
		  this.theDataPointTypeConfiguration.deleteAllDataPointTypes();
		}
		else
		
		 {
			ServerGlobals.getInstance().log("ERROR Cannot delete all DatapointTypes: no configuration");
		}
		return succes;

	}



	public DataPointType getDataPointTypeConfig_ByTypeName(String aDPtypeName) {
		DataPointType foundType;
		foundType = null;
		
		if (this.theDataPointTypeConfiguration == null) {

			openDataPointTypeConfig();
		}
		if (this.theDataPointTypeConfiguration != null) {

			foundType = theDataPointTypeConfiguration.getDataPointTypeConfig_ByTypeName(aDPtypeName);
		} else {
			ServerGlobals.getInstance().log("ERROR No DataPointTypeConfiguration available");
		}
		
		
		return foundType;
	}

}
