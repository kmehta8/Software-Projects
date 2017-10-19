package cas2xb3_finalprototype;

public class LuggageT {
	private String claimNumber;
	private String receivalDate;
	private int receivalMonth;
	private String incidentDate;
	private String airportCode;
	private String knownAirportName;
	private String airlineName;
	private String claimType;
	private String claimSite;
	
	public LuggageT(String claimNumber, String receivalDate, int receivalMonth,
			String incidentDate, String airportCode, String airportName, 
			String airlineName, String claimType, String claimSite){
		/*
		 * This sets all of the possible state variables to an empty String. 
		 */
		this.claimNumber = claimNumber;
		this.receivalDate = receivalDate;
		this.receivalMonth = receivalMonth;
		this.incidentDate = incidentDate;
		this.airportCode = airportCode;
		this.knownAirportName = airportName;
		this.airlineName = airlineName;
		this.claimType = claimType;
		this.claimSite = claimSite;

	}
	
	public void setClaimNumber(String claimNumber){
		this.claimNumber = "";
		this.claimNumber += claimNumber;
	}
	
	public void setReceivalDate(String receivalDate){
		this.receivalDate = "";
		this.receivalDate += receivalDate;
	}
	
	public void setReceivalMonth(int receivalMonth){
		this.receivalMonth = receivalMonth;
	}
	
	public void setIncidentDate(String incidentDate){
		this.incidentDate = "";
		this.incidentDate += incidentDate;
	}
	public void setAirportCode(String airportCode){
		this.airportCode = "";
		this.airportCode += airportCode;
	}
	public void setKnownAirportName(String airportName){
		this.knownAirportName = "";
		this.knownAirportName += knownAirportName;
	}
	public void setAirlineName(String airlineName){
		this.airlineName = "";
		this.airlineName += airlineName;
	}
	public void setClaimType(String claimType){
		this.claimType = "";
		this.claimType += claimType;
	}
	
	public void setClaimSite(String claimSite){
		this.claimSite = "";
		this.claimSite = claimSite;
	}
	
	public String getClaimNumber(){
		return this.claimNumber;
	}
	
	public String getReceivalDate(){
		return this.receivalDate;
	}
	
	public int getReceivalMonth(){
		return this.receivalMonth;
	}
	
	public String getIncidentDate(){
		return this.incidentDate;
	}
	
	public String getAirportCode(){
		return this.airportCode;
	}
	
	public String getKnownAirportName(){
		return this.knownAirportName;
	}
	
	public String getAirlineName(){
		return this.airlineName;
	}
	
	public String getClaimType(){
		return this.claimType;
	}
	
	public String getClaimSite(){
		return this.claimSite;
	}
	
	public int compareTo(String claimNumberOne, String claimNumberTwo){
		return claimNumberOne.compareTo(claimNumberTwo);
	}
}
