package cas2xb3_finalprototype;

public class AirportVertex implements Comparable<AirportVertex> {

	public String name; 	// label for vertex
	public int uid; 		// unique identifier for vertex (index)

	public AirportVertex(String v, int i) {
		name = v;
		uid = i;
	}

	public int getID(){
		return this.uid;
	}
	
	public String toString() {
		return name;
	}

	public int compareTo(AirportVertex other) {
		return name.compareTo(other.name);
	}
}