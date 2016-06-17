package domain;

public class Treatment {
	private int duracion;
	private double reduccionDQO;
	private double reduccionDBO;

	public Treatment(int d, double rDQO, double rDBO) {
		duracion = d;
		reduccionDQO = rDQO;
		reduccionDBO = rDBO;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public double getReduccionDQO() {
		return reduccionDQO;
	}

	public void setReduccionDQO(double reduccionDQO) {
		this.reduccionDQO = reduccionDQO;
	}

	public double getReduccionDBO() {
		return reduccionDBO;
	}

	public void setReduccionDBO(double reduccionDBO) {
		this.reduccionDBO = reduccionDBO;
	}

	public String toString() {
		return "Treatment with duration: " + duracion + " DBO " + reduccionDBO + " DQO " + reduccionDQO;
	}

	public void treatWaterMass(WaterMass w) {
		// System.out.println("tratando masa de agua" + w.toString() + " y
		// aplicando tratamiento que dura " + duracion);
		w.DBO = w.DBO * (1 - reduccionDBO / 100);
		w.DQO = w.DQO * (1 - reduccionDQO / 100);
	}
}
