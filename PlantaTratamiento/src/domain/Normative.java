package domain;

public class Normative {

	public String name;
	public double DBOLimit;
	public double DQOLimit;

	public Normative(String name, double BOLimit, double QOLimit) {
		this.name = name;
		this.DBOLimit = BOLimit;
		this.DQOLimit = QOLimit;
	}

	@Override
	public String toString() {
		return name;
	}

}
