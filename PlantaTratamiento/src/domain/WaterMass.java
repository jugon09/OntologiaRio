package domain;

public class WaterMass {

	public double volume;
	public double DBO;
	public double DQO;

	public WaterMass(double v, double dbo, double dqo) {
		this.volume = v;
		this.DBO = dbo;
		this.DQO = dqo;
	}

	public String toString() {
		return "Water mass with volume " + this.volume + " and DBO: " + this.DBO + " and DQO: " + this.DQO;
	}
}
