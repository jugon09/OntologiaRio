package domain;

public class Industry {

	public double produceDBO;
	public double produceDQO;

	public Industry(double produceDBO, double produceDQO) {
		this.produceDBO = produceDBO;
		this.produceDQO = produceDQO;
	}

	public String toString() {
		return "Industry produce " + this.produceDBO + " of DBO, and " + this.produceDQO + " of DQO";
	}

}
