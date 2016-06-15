package domain;

public class Factory {

	public double capacity;
	public Industry indutry;

	public Factory(double capacity, Industry indutry) {
		this.capacity = capacity;
		this.indutry = indutry;
	}

	public String toString() {
		return "Facorty with capacity " + this.capacity + " of industry : " + indutry.toString();
	}

}
