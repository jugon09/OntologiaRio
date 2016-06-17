package domain;

public class MyTime {

	private int tiempo;

	public MyTime(int t) {
		tiempo = t;
	}

	public int getTime() {
		return tiempo;
	}

	public void addTime(int t) {
		tiempo += t;
	}
}
