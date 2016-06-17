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

	public double getTotalapacity() {
		double capacity = 0;
		for (Tank tank : tanques) {
			capacity += tank.getCapacity();
		}
		return capacity;
	}

	public WaterMass applyAllTreatments(WaterMass w) {
		WaterMass res = new WaterMass(w);
		for (Treatment t : tratamientos)
			t.treatWaterMass(res);
		return res;
	}

	public void applyTreatment(WaterMass w, WaterMass obj,MyTime duracion) {
		int i = 0;
		int n = tratamientos.size();
		if (n > 0) {
			while (w.DBO > obj.DBO || w.DQO > obj.DQO) {
				tratamientos.get(i % n).treatWaterMass(w);
				duracion.addTime(tratamientos.get(i%n).getDuracion());
				++i;
			}
		}
	}
	
	public String toString() {
		String s = "";
		for (Treatment t: tratamientos) {
			s += t.toString();
			s += "\n";
		}
		for (Tank t: tanques) {
			s += t.toString();
			s += "\n";
		}
		return s;
	}
}
