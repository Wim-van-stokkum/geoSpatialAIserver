package nl.geospatialAI.caseContext;

public class Location {
    private String zipCode;
    private String city;
    private String country;
    private String street;
    private String houseNumber;
    private String houseNumberAddition;
    private int bouwKavelNr;
    
    private GPL_coordinate centralCoordinate;
    
    
    public GPL_coordinate getCentralCoordinate() {
		return centralCoordinate;
	}
	public void setCentralCoordinate(GPL_coordinate centralCoordinate) {
		this.centralCoordinate = centralCoordinate;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getHouseNumberAddition() {
		return houseNumberAddition;
	}
	public void setHouseNumberAddition(String houseNumberAddition) {
		this.houseNumberAddition = houseNumberAddition;
	}
	public int getBouwKavelNr() {
		return bouwKavelNr;
	}
	public void setBouwKavelNr(int bouwKavelNr) {
		this.bouwKavelNr = bouwKavelNr;
	}
	
    
    
    
}
