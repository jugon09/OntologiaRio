package domain;

public class Tank {
	private WaterMass wm;
	private double capacity;
	private double usedCapacity;
	
	public Tank(WaterMass w, int c) {
		wm = w;
		capacity = c;
		usedCapacity = wm.volume;
	}
	
	public Tank(int c) {
		capacity = c;
		wm = null;
		usedCapacity = 0;
	}

	public WaterMass getWm() {
		return wm;
	}
	
	public boolean hasEnoughCapacity(WaterMass wm) {
		return usedCapacity + wm.volume <= capacity;
	}

	public void setWm(WaterMass wm) {
		usedCapacity += wm.volume;
		this.wm = wm;
	}

	public double getCapacity() {
		return capacity;
	}
}
