package nl.geospatialAI.DataAccessHandlers;

import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class Planspace_DataAccess {

	private MongoClient planspace_DBclient;
	private MongoDatabase planspace_ConfigDatabase;

	private MongoIterable<String> theCollections;
	private MongoCursor<String> it;
	private ArrayList<String> collectionNames;

	private MongoClientURI connectURI;

	private Planspace_ConfigDB theConfigDB = null; 
	
	
	
	public Planspace_ConfigDB getTheConfigDB() {

		
		if (this.planspace_DBclient == null) {
			this.connectCluster();
		}
		

		if (this.theConfigDB  == null) {
			this.theConfigDB  = new Planspace_ConfigDB(planspace_DBclient);
			
		} 
		return this.theConfigDB;
	}

	public ArrayList<String> getCollections(String dbName) {

		if (this.planspace_DBclient == null) {
			connect(dbName);

		}

		if (this.planspace_DBclient == null) {
			System.out.println("Connectie naar " + dbName + " kon niet worden opgezet");
		}

		else {

			planspace_ConfigDatabase = this.planspace_DBclient.getDatabase(dbName);
			if (planspace_ConfigDatabase == null) {
				System.out.println("Database " + dbName + " kon niet worden gevonden");
			} else {
				theCollections = planspace_ConfigDatabase.listCollectionNames();
				this.it = theCollections.iterator();
				while (it.hasNext()) {

					collectionNames.add(it.next());

				}

			}
		}
		return collectionNames;
	}

	private boolean connect(String dbName) {
		boolean succes;
		connectURI = new MongoClientURI(
				"mongodb://GeoSpatialUser:Ikga2GeoSpatialDB20@cluster0-shard-00-00.4wc3r.mongodb.net:27017,cluster0-shard-00-01.4wc3r.mongodb.net:27017,cluster0-shard-00-02.4wc3r.mongodb.net:27017/"
						+ dbName
						+ "?ssl=true&replicaSet=atlas-11s8w3-shard-0&authSource=admin&retryWrites=true&w=majority");

		succes = false;
		if (this.planspace_DBclient == null) {
			this.planspace_DBclient = new MongoClient(connectURI);
		}

		if (this.planspace_DBclient == null) {
			System.out.println("Kan Mongo client niet aanmaken");

		} else {

			succes = true;
		}

		return succes;
	}
	
	

	
	public boolean connectCluster() {
		boolean succes;
		succes = this.connect("planspace_config");

		return succes;

	}

	

	

	public void sluitCluster() {
		if (this.planspace_DBclient != null) {
			this.planspace_DBclient.close();
		}
	}

}
