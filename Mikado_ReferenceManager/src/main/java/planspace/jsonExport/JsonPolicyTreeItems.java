package planspace.jsonExport;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import planspace.chefModelTypes.ChefTaxonomyItem;

public class JsonPolicyTreeItems {
	
	private List<policyTreeNode> policyTreeItems;
	public JsonPolicyTreeItems() {
		policyTreeItems = new ArrayList<policyTreeNode>();
	}
	
	public void addPolicyItemByChefType(ChefTaxonomyItem aChefTaxItem) {
		policyTreeNode anItem;
		
		anItem = new policyTreeNode();
		anItem.initByChefTaxonomyItem(aChefTaxItem);
		this.policyTreeItems.add(anItem);
	}

	
	public String toJson() {
		/*
		 * Deze methode kan in instantie van deze class omzetten naar Json m.b.v de
		 * JackSon Library (zie POM.XML) Doel is deze Json op te slaan in een Mongo
		 * database. Mongo accepteert echter geen JSON als String, maar vereist de
		 * binaire versie daarvan BSON/Document
		 */

		Gson gsonParser;
		String meAsJson;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}

}
