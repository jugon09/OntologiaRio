package domain;

import java.util.Date;

public class TreatmentDBO extends Treatment {
	private double reduccionDBO;
	
	public TreatmentDBO(Date d, double r) {
		super(d);
		reduccionDBO = r;
	}

	@Override
	public WaterMass treatWaterMass(WaterMass w) {
		w.DBO = w.DBO * (1 - reduccionDBO/100);
		return w;
	}

	@Override
	public String toString() {
		return "TreatmentDBO takes " + super.getDuracion().toString() + " and reduces DBO in " + reduccionDBO
				+ " %";
	}
}
