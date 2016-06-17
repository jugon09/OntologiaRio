package domain;

import java.util.Date;

public class TreatmentDQO extends Treatment {
	private double reduccionDQO;
	
	public TreatmentDQO(Date c, double r) {
		super(c);
		reduccionDQO = r;
	}
	@Override
	public String toString() {
		return "TreatmentDBO takes " + super.getDuracion().toString() + " and reduces DQO in " + reduccionDQO
				+ " %";
	}

	@Override
	public WaterMass treatWaterMass(WaterMass w) {
		w.DBO = w.DBO * (1 - reduccionDQO/100);
		return null;
	}

}
