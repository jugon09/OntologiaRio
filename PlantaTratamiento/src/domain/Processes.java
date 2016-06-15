package domain;

import java.util.List;

public class Processes {

	private WaterMass mergeWater(WaterMass w1, WaterMass w2) {
		// Merge volum
		double volum = w1.volume + w2.volume;
		// Merge DBO
		double dbo = ((w1.DBO * w1.volume) + (w2.DBO * w2.volume)) / volum;
		// Merge DBQ
		double dqo = ((w1.DQO * w1.volume) + (w2.DQO * w2.volume)) / volum;
		// Watermass result
		return new WaterMass(volum, dbo, dqo);
	}

	public WaterMass mergeWater(List<WaterMass> listOfWater) {
		WaterMass result = null;
		WaterMass tmp = listOfWater.get(0);
		for (int i = 1; i < listOfWater.size(); i++) {
			result = mergeWater(tmp, listOfWater.get(i));
			tmp = result;
		}
		return result;
	}

	public WaterMass mergeWaterEfficient(List<WaterMass> listOfWater) {
		WaterMass result = null;
		WaterMass tmp = listOfWater.get(0);
		for (int i = 1; i < listOfWater.size(); i++) {
			result = mergeWater(tmp, listOfWater.get(i));
			tmp = result;
		}
		return result;
	}

}