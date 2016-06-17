package domain;

public class Treatment {

	private int duracion;
	private double reduccionDQO;
	private double reduccionDBO;
	private double reduccionSST;
	private double reduccionNitrates;
	private double reduccionPhosphates;

	public Treatment(int duracion, double reduccionDQO, double reduccionDBO, double reduccionSST,
			double reduccionNitrates, double reduccionPhosphates) {
		this.duracion = duracion;
		this.reduccionDQO = reduccionDQO;
		this.reduccionDBO = reduccionDBO;
		this.reduccionSST = reduccionSST;
		this.reduccionNitrates = reduccionNitrates;
		this.reduccionPhosphates = reduccionPhosphates;
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

	public double getReduccionSST() {
		return reduccionSST;
	}

	public void setReduccionSST(double reduccionSST) {
		this.reduccionSST = reduccionSST;
	}

	public double getReduccionNitrates() {
		return reduccionNitrates;
	}

	public void setReduccionNitrates(double reduccionNitrates) {
		this.reduccionNitrates = reduccionNitrates;
	}

	public double getReduccionPhosphates() {
		return reduccionPhosphates;
	}

	public void setReduccionPhosphates(double reduccionPhosphates) {
		this.reduccionPhosphates = reduccionPhosphates;
	}

	public String toString() {
		return "Treatment with duration: " + duracion + ", DBO: " + reduccionDBO + ", DQO: " + reduccionDQO + ", SST:"
				+ reduccionSST + ", Nitrates: " + reduccionNitrates + " and Phosphates : " + reduccionPhosphates
				+ " reduccions";
	}

	public void treatWaterMass(WaterMass w) {
		// System.out.println("tratando masa de agua" + w.toString() + " y
		// aplicando tratamiento que dura " + duracion);
		w.DBO = w.DBO * (1 - reduccionDBO / 100);
		w.DQO = w.DQO * (1 - reduccionDQO / 100);
		w.SST = w.SST * (1 - reduccionSST / 100);
		w.Nitrates = w.Nitrates * (1 - reduccionNitrates / 100);
		w.Phosphates = w.Phosphates * (1 - reduccionPhosphates / 100);
	}
}
