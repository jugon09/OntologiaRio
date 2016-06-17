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

	public WaterMass(WaterMass w) {
		this.volume = w.volume;
		this.DBO = w.DBO;
		this.DQO = w.DQO;
	}

	public String toString() {
		return "Water mass with volume " + this.volume + " and DBO: " + this.DBO + " and DQO: " + this.DQO;
	}
}
