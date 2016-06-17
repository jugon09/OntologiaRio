package domain;

public class Normative {

	public String name;

	public double DBOLimit;
	public double DQOLimit;
	public double SSTLimit;
	public double NitratesLimit;
	public double PhosphatesLimit;

	public Normative(String name, double dBOLimit, double dQOLimit, double sSTLimit, double nitratesLimit,
			double phosphatesLimit) {
		this.name = name;
		DBOLimit = dBOLimit;
		DQOLimit = dQOLimit;
		SSTLimit = sSTLimit;
		NitratesLimit = nitratesLimit;
		PhosphatesLimit = phosphatesLimit;
	}

	@Override
	public String toString() {
		return name;
	}

}
