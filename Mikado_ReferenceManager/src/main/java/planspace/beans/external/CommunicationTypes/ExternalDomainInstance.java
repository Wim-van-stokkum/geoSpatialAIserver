package planspace.beans.external.CommunicationTypes;

import java.util.ArrayList;
import java.util.List;

import planspace.instanceModel.DataPointValue;

public class ExternalDomainInstance {

	String myObjectType;

	// De instanties kan waarden hebben voor 0 of meerdere DataPointTypes vam het
	// ObjectType
	public List<ExternalDatapointValue> dataPointValues;
	
	
	public ExternalDomainInstance() {
		dataPointValues = new ArrayList<ExternalDatapointValue>();
	}


	public String getMyObjectType() {
		return myObjectType;
	}


	public void setMyObjectType(String myObjectType) {
		this.myObjectType = myObjectType;
	}


	public List<ExternalDatapointValue> getDataPointValues() {
		return dataPointValues;
	}


	public void setDataPointValues(List<ExternalDatapointValue> dataPointValues) {
		this.dataPointValues = dataPointValues;
	}


	
}
