package domain;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		String ontologyPath = "./resources/onto1.owl";
		String NamingContext = "http://www.semanticweb.org/camilo/ontologies/2016/4/ontologiaRio#";

		System.out.println("----------------Starting program -------------");

		JenaManager jManager = new JenaManager(ontologyPath, NamingContext);

		jManager.loadOntology();
		
		

		List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
		for (WaterMass watermass : listOfWater)
			System.out.println(watermass.toString());

		String nameProcess = jManager.executeQuery();
		System.out.println(nameProcess);

		Method method = null;
		Processes p = new Processes();
		try {
			method = p.getClass().getMethod(nameProcess, WaterMass.class, WaterMass.class);

		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}

		WaterMass w3 = null;
		try {
			w3 = (WaterMass) method.invoke(null, listOfWater.get(0), listOfWater.get(1));
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

		// Watermass w3 = Processes.mergeWater(w1, w2);
		if (w3 != null) {
			jManager.addWatermassIndividual(w3);
			System.out.println(w3.toString());
		}

		jManager.releaseOntology();

		System.out.println("--------- Program terminated --------------------");
	}
}