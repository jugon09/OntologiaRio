package domain;

import domain.WaterMass;

public class Processes {

	public static WaterMass mergeWater(WaterMass w1, WaterMass w2) {
		// Merge volum
		double volum = w1.volume + w2.volume;
		// Merge DBO
		double dbo = ((w1.DBO * w1.volume) + (w2.DBO * w2.volume)) / volum;
		// Merge DBQ
		double dqo = ((w1.DQO * w1.volume) + (w2.DQO * w2.volume)) / volum;
		// Watermass result
		return new WaterMass(volum, dbo, dqo);
	}

}