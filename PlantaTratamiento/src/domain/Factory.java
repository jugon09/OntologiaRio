package domain;

public class Factory {

	public double capacity;
	public Industry industry;

	public Factory(double capacity, Industry industry) {
		this.capacity = capacity;
		this.industry = industry;
	}

	public String toString() {
		return "Factory with capacity " + this.capacity + " of industry : " + industry.toString();
	}

}
