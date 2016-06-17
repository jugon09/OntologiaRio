package domain;

import java.util.ArrayList;

public class TreatmentPlant {
	private ArrayList<Treatment> tratamientos;
	
	public TreatmentPlant(ArrayList<Treatment> t) {
		tratamientos = t;
	}

	public ArrayList<Treatment> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(ArrayList<Treatment> tratamientos) {
		this.tratamientos = tratamientos;
	}
	
	public WaterMass applyAllTreatments(WaterMass w) {
		WaterMass res = new WaterMass(w);
		for (Treatment t: tratamientos) res = t.treatWaterMass(res);
		return res;
	}
}
