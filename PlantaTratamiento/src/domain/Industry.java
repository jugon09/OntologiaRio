package domain;

public class Industry {

	public double produceDBO;
	public double produceDQO;
	public double produceSST;
	public double produceNitrates;
	public double producePhosphates;

	public Industry(double produceDBO, double produceDQO, double produceSST, double produceNitrates,
			double producePhosphates) {
		this.produceDBO = produceDBO;
		this.produceDQO = produceDQO;
		this.produceSST = produceSST;
		this.produceNitrates = produceNitrates;
		this.producePhosphates = producePhosphates;
	}

	public String toString() {
		return "Industry produce " + produceDBO + " of DBO, and " + produceDQO + " of DQO, and " + produceSST
				+ " of SST, and " + produceNitrates + " of Nitrates, and " + producePhosphates + "of Phosphates";
	}

}
