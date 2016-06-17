package domain;

import java.util.Date;

public abstract class Treatment {
	private WaterMass wm;
	private Date duracion;
	
	public Treatment(Date d) {
		duracion = d;
	}
	public Date getDuracion() {
		return duracion;
	}

	public void setDuracion(Date duracion) {
		this.duracion = duracion;
	}
	public String toString() {
		return "Treatment applied to " + wm.toString() + " and duration " + duracion.toString();
	}
	
	public abstract WaterMass treatWaterMass(WaterMass w);
}
