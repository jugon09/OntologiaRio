package domain;

import java.util.ArrayList;

public class TreatmentPlant {
	private ArrayList<Treatment> tratamientos;
	private ArrayList<Tank> tanques;
	
	public TreatmentPlant(ArrayList<Treatment> t, ArrayList<Tank> tan) {
		tratamientos = t;
		tanques = tan;
	}

	public ArrayList<Treatment> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(ArrayList<Treatment> tratamientos) {
		this.tratamientos = tratamientos;
	}
	
	public ArrayList<Tank> getTanques() {
		return tanques;
	}

	public void setTanques(ArrayList<Tank> tanques) {
		this.tanques = tanques;
	}

	public WaterMass applyAllTreatments(WaterMass w) {
		WaterMass res = new WaterMass(w);
		for (Treatment t: tratamientos) t.treatWaterMass(res);
		return res;
	}
	
	public WaterMass applyTreatment(WaterMass w,WaterMass obj) {
		WaterMass res = new WaterMass(w);
		int i = 0;
		int n = tratamientos.size();
		if (n > 0) {
			while (res.DBO > obj.DBO && res.DQO > obj.DQO) {
				tratamientos.get(i%n).treatWaterMass(res);
				++i;
			}
		}
		return res;
	}
}
