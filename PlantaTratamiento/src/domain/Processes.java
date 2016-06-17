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
		// Merge SST
		double sst = ((w1.SST * w1.volume) + (w2.SST * w2.volume)) / volum;
		// Merge Nitrates
		double nit = ((w1.Nitrates * w1.volume) + (w2.Nitrates * w2.volume)) / volum;
		// Merge Phosphates
		double pho = ((w1.Phosphates * w1.volume) + (w2.Phosphates * w2.volume)) / volum;
		// Watermass result
		return new WaterMass(volum, dbo, dqo, sst, nit, pho);
	}

	public WaterMass mergeWater(List<WaterMass> listOfWater) {
		WaterMass result = listOfWater.get(0);
		WaterMass tmp = listOfWater.get(0);
		for (int i = 1; i < listOfWater.size(); i++) {
			result = mergeWater(tmp, listOfWater.get(i));
			tmp = result;
		}
		return result;
	}

	public WaterMass mergeWaterEfficient(List<WaterMass> listOfWater) {
		WaterMass result = listOfWater.get(0);
		WaterMass tmp = listOfWater.get(0);
		for (int i = 1; i < listOfWater.size(); i++) {
			result = mergeWater(tmp, listOfWater.get(i));
			tmp = result;
		}
		return result;
	}

	public WaterMass generateIndustryWaterMassSimple(Factory factory) {
		double volum = factory.capacity;
		double DBO = factory.industry.produceDBO;
		double DQO = factory.industry.produceDQO;
		double SST = factory.industry.produceSST;
		double NIT = factory.industry.produceNitrates;
		double PHO = factory.industry.producePhosphates;
		return new WaterMass(volum, DBO, DQO, SST, NIT, PHO);
	}

	public WaterMass purifyWater(TreatmentPlant tp, WaterMass wm) {
		return tp.applyAllTreatments(wm);
	}

	public WaterMass timeToReducePollutant(TreatmentPlant tp, WaterMass wm, WaterMass obj, MyTime duracion) {
		tp.applyTreatment(wm, obj, duracion);
		return wm;
	}

	public void treatmentPlantEfficiency(TreatmentPlant tp, List<WaterMass> listOfWater) {
		WaterMass inWater = mergeWater(listOfWater);
		WaterMass outWater = null;
		double volumDiff = inWater.volume - tp.getTotalapacity();
		if (volumDiff > 0) {
			// Watermass remaining
			WaterMass remainingWater = new WaterMass(volumDiff, inWater.DBO, inWater.DQO, 0, 0, 0);
			WaterMass procesedWater = new WaterMass(tp.getTotalapacity(), inWater.DBO, inWater.DQO, 0, 0, 0);
			WaterMass purifiedWater = purifyWater(tp, procesedWater);
			outWater = mergeWater(purifiedWater, remainingWater);
		} else {
			outWater = purifyWater(tp, inWater);
		}
		// Efficiency
		int DBO = (int) (((inWater.DBO - outWater.DBO) / inWater.DBO) * 100);
		System.out.println("DBO efficiency : " + DBO + "%");
		int DQO = (int) (((inWater.DQO - outWater.DQO) / inWater.DQO) * 100);
		System.out.println("DQO efficiency : " + DQO + "%");
	}

	public void classifyWaterNormative(WaterMass w, List<Normative> norms) {
		System.out.println("The " + w);
		for (Normative normative : norms) {
			boolean comply = w.DBO <= normative.DBOLimit;
			comply = comply && w.DQO <= normative.DQOLimit;
			comply = comply && w.SST <= normative.SSTLimit;
			comply = comply && w.Nitrates <= normative.NitratesLimit;
			comply = comply && w.Phosphates <= normative.PhosphatesLimit;
			if (comply)
				System.out.println("\tComply with Normative : " + normative);
			else
				System.out.println("\tNot Comply with Normative : " + normative);

		}

	}

}