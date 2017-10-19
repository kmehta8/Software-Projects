package cas2xb3_finalprototype;
import edu.princeton.cs.algs4.*;

public class LuggageGraph {

	private final int airlineVertices; //Number of Vertices
	private final int airportVertices;
	private int E; //Number of Edges
	private Edge[][] airlineAdj;
	private Edge[][] airportAdj;
	
	/**
	 * Initializes empty edge-weighted graph for the lubbage with V vertices and 0 edges
	 * 
	 * @param V number of vertices
	 * @throws IllegalArgumentException if the number of vertices is negative.
	 */
	public LuggageGraph(int airlineVerticeNumber, int airportVerticeNumber) {
		if ((airlineVerticeNumber)< 0 || 
				airportVerticeNumber < 0) throw new IllegalArgumentException("Number of vertices cannot be negative");
		this.airlineVertices = airlineVerticeNumber;
		this.airportVertices = airportVerticeNumber;
		this.E = 0;
		airlineAdj = new Edge[airlineVerticeNumber][];
		for (int v = 0; v < airlineVerticeNumber; v++) {
			airlineAdj[v] = new Edge[airportVerticeNumber];
		}
		
		airportAdj = new Edge[airportVerticeNumber][];
		for (int v = 0; v < airportVerticeNumber; v++) {
			airportAdj[v] = new Edge[airlineVerticeNumber];
		}
	}
	/**
	 * Returns number of airport vertices
	 * @return number of airport vertices
	 */
	public int getAirportVerticeNumber() {
		return this.airportVertices;
	}
	
	
	/**
	 * Returns the number of airline vertices.
	 * @return This returns the number of total airlines. 
	 */
	public int getAirlineVerticeNumber(){
		return this.airlineVertices;
	}
	
	/**
	 * Returns number of Edges
	 * @return number of edges
	 */
	public int E() {
		return E;
	}
	
	public void addEdge(Edge e) {
		int v = e.airportVertex().uid;
		int w = e.airlineVertex().uid;
		
		if (airportAdj[v][w] == null){
			airportAdj[v][w] = e;
			airlineAdj[w][v] = e;
			E++;
		}
		else{
			airportAdj[v][w].increaseWeight();
			airlineAdj[v][w].increaseWeight();
		}
	}
	
	
	public int ranking (AirlineVertex airline){
		return 0;
	}
	/**
	public Iterable<Edge> airportAdj(int v) {
		return airportAdj[v];
	}
	
	public Iterable<Edge> airlineAdj(int v) {
		return airlineAdj[v];
	} **/
	
//	public LuggageGraph(LuggageT[] in) {
//		
//	}
	
}
