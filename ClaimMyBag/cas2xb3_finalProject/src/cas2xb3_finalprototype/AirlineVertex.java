package cas2xb3_finalprototype;

public class AirlineVertex implements Comparable<AirlineVertex> {

	public String name; 	// label for vertex
	public int uid; 		// unique identifier for vertex (index)

	public AirlineVertex(String v, int i) {
		name = v;
		uid = i;
	}

	public int getID(){
		return this.uid;
	}
	
	public String toString() {
		return name;
	}

	public int compareTo(AirlineVertex other) {
		return name.compareTo(other.name);
	}
}
