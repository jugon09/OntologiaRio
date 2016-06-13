package domain;
import domain.Watermass;

public class Processes {
	public static float addVolume(float v1, float v2){
		return v1+v2;
	}
	
	public static float getAmount (float c, float v){
		return c*v;
	}
	
	public static float mergeAmounts (float c1, float v1, float c2, float v2){
		return getAmount(c1,v1)+getAmount(c2,v2);
	}
	
	public static float mergeConcentration(float c1, float v1, float c2, float v2){
		return mergeAmounts(c1,v1,c2,v2)/addVolume(v1,v2);
	}

	public static Watermass mergeWater(Watermass w1, Watermass w2){
		float v3 = addVolume(w1.volume, w2.volume);
		float dbo3 = mergeConcentration(w1.volume, w1.dbo, w2.volume, w2.dbo);
		return new Watermass(v3, dbo3);
	}
	
	public static Watermass efficientMergeWater(Watermass w1, Watermass w2){
		return new Watermass(w1.volume+w2.volume, (w1.dbo*w1.volume+w2.dbo*w2.volume)/w1.volume+w2.volume);
	}
}