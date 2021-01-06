package nl.geospatialAI.DataAccessHandlers;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import nl.geospatialAI.DataPoints.DataPointType;
import nl.geospatialAI.serverGlobals.ServerGlobals;


public class Planspace_Datapoint_ConfigDB {
	private MongoClient planspace_DBclient = null;
	private MongoDatabase planspace_ConfigDatabase = null;
	private MongoCollection planspace_DP_collection = null;
	private String dataPoint_CollectionName = "datapoint_types";

	public Planspace_Datapoint_ConfigDB(MongoClient aMongoClient, MongoDatabase aPlanspace_ConfigDatabase) {
		this.planspace_DBclient = aMongoClient;
		this.planspace_ConfigDatabase = aPlanspace_ConfigDatabase;
		this.planspace_DP_collection = this.planspace_ConfigDatabase.getCollection(dataPoint_CollectionName);
		// if (checkAvailability()) {
		// ServerGlobals.getInstance().log("SUCCES: configuration collection " +
		// this.dataPoint_CollectionName + " available");
		// }

	}


	private boolean checkAvailability() {
		boolean available;

		available = true;
	

		return available;

	}

	public void storeDataPointType(DataPointType aDataPointType) {
		Document theDP_asDocument;

		if (this.checkAvailability()) {

			theDP_asDocument = aDataPointType.toMongoDocument();

			this.planspace_DP_collection.insertOne(theDP_asDocument);
		} else {
			ServerGlobals.getInstance().log("ERROR: storing DataPointType " + aDataPointType.getTypeName());
		}

	}

	public void deleteAllDataPointTypes() {

		String emptyAsJson;
		Document emptyAsDocument;

		emptyAsJson = "{}";

		// ServerGlobals.getInstance().log("JSON for DataPointType " + this.typeName +
		// "-> " + meAsJson);

		emptyAsDocument = Document.parse(emptyAsJson);

		if (this.checkAvailability()) {

			this.planspace_DP_collection.deleteMany(emptyAsDocument);
		} else {
			ServerGlobals.getInstance().log("ERROR: deleting all DataPointType in " + this.dataPoint_CollectionName);
		}

	}


	public DataPointType getDataPointTypeConfig_ByTypeName(String aDPtypeName) {
		DataPointType foundDataPointType;

		Document query;
		FindIterable queryResult;
		MongoCursor queryResultCursor ;
		Object foundDPType;
		Gson gsonParser;
		String meAsJson;
		
		foundDataPointType = null;
		if (this.checkAvailability()) {

			query = new Document();
			query.append("typeName", aDPtypeName);
			
			queryResult = this.planspace_DP_collection.find(query);
			queryResultCursor  = queryResult.cursor();
			while (queryResultCursor.hasNext()) {
				foundDPType =   queryResultCursor.next();
				gsonParser = new Gson();
				meAsJson = gsonParser.toJson(foundDPType);
				
				System.out.println("Gevonden: " + meAsJson);
				foundDataPointType = gsonParser.fromJson(meAsJson, DataPointType.class);
			}

		} else {
			ServerGlobals.getInstance().log("ERROR: No document in confog DB for DataPointType " + aDPtypeName);
		}
		return foundDataPointType;
	}

}
