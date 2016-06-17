package domain;

import java.util.Date;

public abstract class Treatment {
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
	
	public abstract String toString();
	
	public abstract WaterMass treatWaterMass(WaterMass w);
}
