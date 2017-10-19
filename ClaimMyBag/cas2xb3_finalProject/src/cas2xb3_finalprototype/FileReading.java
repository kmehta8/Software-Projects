package cas2xb3_finalprototype;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * The purpose of this class is to be able to read from a text file that is directly
 * correlating with a dataset that was retrieved as an Excel file.
 * The claims file that is provided contains historical information on claim numbers
 * and lost luggage found at specific destinations.
 * 
 * This will organize all pieces of luggage into an abstract data type consisting 
 * of setters and getters. 
 * @author SE2XB3 Group 11
 *
 */
public class FileReading {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		ArrayList<LuggageT> totalLuggage = new ArrayList<LuggageT>();
		
		FileInputStream fstream = new FileInputStream("claims-2002-2006_0.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		
		//This ensures that the headers are not included inside of a LuggageT object.
		br.readLine();
		
		
		//This will read the dataset line by line until it reaches a line 
		//that is identified as null. 
		int count = 0;
		while ((strLine = br.readLine()) != null) {
			// This splits the line by the tab characters in order to properly
			// segment the contents of the Luggage.
			String[] splitting = strLine.split("\t", -1);
			
			System.out.println(splitting[1]);
			int month = 0;
			
			
			if (splitting[1].equals("")){
				month = 0;
			}
			else{
				
				String splitReceivalData = splitting[1].split("-")[1];
	
				switch (splitReceivalData) {
					case "Jan":
						month = 1;
					case "01":
						month = 1;
						break;
					case "Feb":
						month = 2;
						break;
					case "02":
						month = 2;
						break;
					case "Mar":
						month = 3;
						break;
					case "03":
						month = 3;
						break;
					case "Apr":
						month = 4;
						break;
					case "04":
						month = 4;
						break;
					case "May":
						month = 5;
						break;
					case "05":
						month = 5;
						break;
					case "Jun":
						month = 6;
						break;
					case "06":
						month = 6;
						break;
					case "Jul":
						month = 7;
						break;
					case "07":
						month = 7;
						break;
					case "Aug":
						month = 8;
						break;
					case "08":
						month = 8;
						break;
					case "Sep":
						month = 9;
						break;
					case "09":
						month = 9;
						break;
					case "Oct":
						month = 10;
						break;
					case "10":
						month = 10;
						break;
					case "Nov":
						month = 11;
						break;
					case "11":
						month = 11;
						break;
					case "Dec":
						month = 12;
						break;
					case "12":
						month = 12;
						break;
	
				} 
			} 
			totalLuggage.add(new LuggageT(splitting[0], splitting[1], month, 
					splitting[2], splitting[3],
					splitting[4], splitting[5], splitting[6], splitting[7]));
			count++;
		}

		//This is the portion where the luggage is sorted out by the month 
		//and that the new, appropriate airport and airline vertices
		//are created. 
		LuggageT correctLuggage = totalLuggage.get(100);
		int foundLuggageMonth = correctLuggage.getReceivalMonth();
		
		//This creates an ArrayList consisting of all luggages inside the total luggage amount 
		//inside of the dataset.
		ArrayList<LuggageT> monthLuggage = new ArrayList<LuggageT>();
		
		//These ArrayLists consist of all possible airline and airport vertices inside
		//the total luggage.
		ArrayList<AirlineVertex> airlineVertices = new ArrayList<AirlineVertex>();
		ArrayList<AirportVertex> airportVertices = new ArrayList<AirportVertex>();
		
		
		
		int airlineID = 0;
		boolean airlineFlag = true;
		int airportID = 0;
		boolean airportFlag = true;
		for (int i = 0; i < totalLuggage.size(); i ++){
			if (totalLuggage.get(i).getReceivalMonth() == foundLuggageMonth){
				monthLuggage.add(totalLuggage.get(i));
			}
			
			for (int j = 0; j < airlineVertices.size(); j ++){
				if (totalLuggage.get(i).getAirlineName() == airlineVertices.get(j).toString()){
					airlineFlag = false;
				}
			}
			
			for (int k = 0; k < airportVertices.size(); k ++){
				if (totalLuggage.get(i).getKnownAirportName() == airportVertices.get(k).toString()){
					airportFlag = false;
				}
			}
			
			if (airlineVertices.size() == 0 || airlineFlag == false){
				airlineVertices.add(new AirlineVertex(totalLuggage.get(i).getAirlineName(), airlineID));
				airlineID ++;
			}
			
			if (airportVertices.size() == 0 || airportFlag == false){
				airportVertices.add(new AirportVertex(totalLuggage.get(i).getKnownAirportName(), airportID));
				airportID ++;
			}
			
			airlineFlag = true;
			airportFlag = true;
		}
		
		//This is where the graph is created using its constructor.
		//The number of airline vertices and airport vertices are provided 
		//in order to instantiate the object for the LuggageGraph.
		LuggageGraph monthGraph = new LuggageGraph(airlineVertices.size(),airportVertices.size());
		
		
		//Creates an airport vertex and airline vertex that is set to one of the existing
		//airline or airport vertices inside of the arrayList.
		//This is to ensure that the positioning in the graph is kept in check
		//relating to their ID numbers. 
		AirportVertex swagger = new AirportVertex("", 0);
		AirlineVertex swaggerOne = new AirlineVertex("", 0);
		
		
		//This for loop is to go through all luggage of the month to add appropriate
		//pairs of edges to the graph. 
		for (int i = 0; i < monthLuggage.size(); i ++){
			//Checks for an invalid pairing of airline and vertex.
			//This only happens if one of the luggage items has a 
			//blank parameter of either the airline or airport. 
			if(monthLuggage.get(i).getAirlineName() == "" || 
				monthLuggage.get(i).getKnownAirportName() == ""){
				continue;
			}
			else{
				
				
				//This goes through all airline vertices to find a matching airline vertex.
				for (int j = 0; j < airlineVertices.size(); j++){
					if (monthLuggage.get(i).getAirlineName() == airlineVertices.get(j).toString()){
						swaggerOne = airlineVertices.get(j);
					}
				}
				
				//This goes through all airport vertices to find a matching airport vertex.
				for (int k = 0; k < airportVertices.size(); k++){
					if (monthLuggage.get(i).getAirlineName() == airportVertices.get(k).toString()){
						swagger = airportVertices.get(k);
					}	
				}
				
				//This creates an edge to be added to the created graph.
				//This is later used for analysis data. 
				Edge connectionEdge = new Edge(swagger, swaggerOne, 1);
				monthGraph.addEdge(connectionEdge);
			}
		}
		
		//Close the input stream
		br.close();
	}

}
