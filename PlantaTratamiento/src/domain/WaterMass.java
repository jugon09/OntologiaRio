package domain;

public class WaterMass {

	public double volume;
	public double DBO;
	public double DQO;
	public double SST;
	public double Nitrates;
	public double Phosphates;

	public WaterMass(double volume, double dBO, double dQO, double sST, double nitrates, double phosphates) {
		this.volume = volume;
		DBO = dBO;
		DQO = dQO;
		SST = sST;
		Nitrates = nitrates;
		Phosphates = phosphates;
	}

	public WaterMass(WaterMass w) {
		volume = w.volume;
		DBO = w.DBO;
		DQO = w.DQO;
		SST = w.SST;
		Nitrates = w.Nitrates;
		Phosphates = w.Phosphates;
	}

	public String toString() {
		return "Water mass with volume " + this.volume + " and DBO: " + this.DBO + " and DQO: " + this.DQO
				+ " and SST: " + SST + " and Nitrates: " + Nitrates + " and Phosphates: " + Phosphates;
	}
}
