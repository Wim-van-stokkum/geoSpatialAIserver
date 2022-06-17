package planspace.jsonExport;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;

public class JsonPolicyItems {
	
	private List<policyItem> policyItems;
	private List<policyTreeNode> policyTreeItems;
	
	public JsonPolicyItems() {
		policyItems = new ArrayList<policyItem>();
		policyTreeItems = new ArrayList<policyTreeNode>();
	}
	
	public void addPolicyItemByChefType(ChefType aChefType) {
		policyItem anItem;
		
		anItem = new policyItem();
		anItem.initByChefType(aChefType);
		this.policyItems.add(anItem);
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

	public boolean isEmpty() {
	
		return (this.policyItems.size() == 0 );
	}

	
		
	public void addPolicyTreeItemByChefTaxItem(ChefTaxonomyItem aChefTaxItem) {
		policyTreeNode anItem;
		
		anItem = new policyTreeNode();
		anItem.initByChefTaxonomyItem(aChefTaxItem);
		this.policyTreeItems.add(anItem);
	}

	public void addPolicyTreeItemByChefTreeContext(ChefTreeContext aChefTypeContext) {
		policyTreeNode anItem;
		
		anItem = new policyTreeNode();
		anItem.initByChefTreeContext(aChefTypeContext);
		this.policyTreeItems.add(anItem);
		
	}

	

}
