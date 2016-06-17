package domain;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final String ONTO_PATH = "../Ont.owl";
	private static final String NAMING_CONTEXT = "http://www.semanticweb.org/camilo/ontologies/2016/4/ontologiaRio#";

	private Scanner sc;
	private JenaManager jManager;
	private Processes processes;

	public void start() throws Exception {
		processes = new Processes();
		sc = new Scanner(System.in);
		jManager = new JenaManager(ONTO_PATH, NAMING_CONTEXT);
		jManager.loadOntology();
		// operation loops
		int operation = -1;
		while (operation != 0) {
			printMenu();
			System.out.print("Process : ");
			operation = sc.nextInt();
			System.out.println();
			switch (operation) {
			case 0:
				break;
			case 1:
				System.out.println(" -- Generating a water mass of an industrial process --");
				generateWatermass();
				break;
			case 2:
				System.out.println(" -- Merge some water masses --");
				mergeWatermass();
				break;
			case 3:
				System.out.println(" -- Water mass classification based on a normative --");
				classificationOfWatermass();
				break;
			case 4:
				System.out.println(" -- Water purification of a  water mass --");
				purificationOfWatermass();
				break;
			case 5:
				System.out.println(" -- Time required to purify a water mass to a certain level --");
				timeToPurifyWatermass();
				break;
			case 6:
				System.out.println(" -- Efficiency of residual water treatment plant --");
				efficienfyOfPlant();
				break;
			default:
				System.out.println("Wrong operation");
			}
			if (operation > 0 && operation < 7) {
				System.out.print("\nPress Enter To Continue");
				sc.nextLine();
			}
			System.out.println();
		}
		// Save Ontology
		// jManager.releaseOntology();
	}

	public void printMenu() {
		System.out.println("****************	Chose Process	****************");
		System.out.println("1. Generating a water mass of an industrial process");
		System.out.println("2. Merge some water masses");
		System.out.println("3. Water mass classification based on a normative");
		System.out.println("4. Water purification of a  water mass");
		System.out.println("5. Time required to purify a water mass to a certain level");
		System.out.println("6. Efficiency of residual water treatment plant ");
	}

	public String getFunctionOfProcess(String process) {
		List<String> functionsNames = jManager.findAllProcessesOf(process);
		if (functionsNames.isEmpty()) {
			System.out.println("\nNo Functions of " + process + "\n");
			return null;
		}
		System.out.println("\n-Chose Function-");
		for (int i = 0; i < functionsNames.size(); i++)
			System.out.println(i + ". " + functionsNames.get(i));
		int op = -1;
		do {
			System.out.print("\nFunction : ");
			op = sc.nextInt();
			if (op >= functionsNames.size() || op < 0)
				System.out.println("Wrong function");
		} while (op >= functionsNames.size() || op < 0);
		return functionsNames.get(op);
	}

	/**
	 * Generación de una masa de agua resultante de un proceso industrial
	 */
	public void generateWatermass() throws Exception {
		String nameFunction = getFunctionOfProcess("GenerateIndustryWaterMass");
		if (nameFunction != null) {
			System.out.println("Name of Function : " + nameFunction);
			// Parametro factory
			System.out.println("-Chose Factory-");
			List<Factory> factories = jManager.getAllFactoryIndividuals();
			for (int i = 0; i < factories.size(); i++)
				System.out.println(i + ". " + factories.get(i).toString());
			System.out.print("Factory : ");
			int id = sc.nextInt();
			sc.nextLine();
			Factory factory = factories.get(id);
			// generacion masas de agua de una fabrica
			Method method = processes.getClass().getMethod(nameFunction, Factory.class);
			WaterMass wm = (WaterMass) method.invoke(processes, factory);
			if (wm != null) {
				jManager.addWatermassIndividual(wm);
				System.out.println("New Water Mass Generated : " + wm.toString());
			} else
				System.out.println("Not Generated");
		}

	}

	/**
	 * Mezcla de aguas
	 */
	public void mergeWatermass() throws Exception {
		String nameFunction = getFunctionOfProcess("MergeWater");
		if (nameFunction != null) {
			System.out.println("Name of Function : " + nameFunction);
			// parametros masas de agua
			System.out.println("-Chose list of Watermasses-");
			List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
			for (int i = 0; i < listOfWater.size(); i++)
				System.out.println(i + ". " + listOfWater.get(i).toString());
			sc.nextLine();
			System.out.print("List of ids [0 1 4 5 . . .] : ");
			String ids = sc.nextLine();
			String[] splited = ids.split("\\s+");
			List<WaterMass> parameters = new ArrayList<>();
			for (int i = 0; i < splited.length; i++) {
				int id = Integer.valueOf(splited[i]);
				parameters.add(listOfWater.get(id));
			}
			// merge list of watermass
			WaterMass w3 = null;
			Method method = processes.getClass().getMethod(nameFunction, List.class);
			w3 = (WaterMass) method.invoke(processes, parameters);
			if (w3 != null) {
				jManager.addWatermassIndividual(w3);
				System.out.println("New Water Mass Merged : " + w3.toString());
			} else
				System.out.println("Not Merged");
		}

	}

	public void classificationOfWatermass() throws Exception {
		String nameFunction = getFunctionOfProcess("ClassifyWaterNormative");
		if (nameFunction != null) {
			System.out.println("-Choose Watermass-");
			List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
			for (int i = 0; i < listOfWater.size(); i++)
				System.out.println(i + ". " + listOfWater.get(i).toString());
			System.out.print("Choose number of waterMass");
			int id = sc.nextInt();
			sc.next();
			// TODO
			return;
		}

	}

	public void purificationOfWatermass() throws Exception {
		String nameFunction = getFunctionOfProcess("PurifyWater");
		if (nameFunction != null) {
			System.out.println("Name of Function : " + nameFunction);
			System.out.println("-Choose list of Watermasses-");
			List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
			for (int i = 0; i < listOfWater.size(); i++)
				System.out.println(i + ". " + listOfWater.get(i).toString());
			System.out.println("Choose number of waterMass");
			int id = sc.nextInt();
			WaterMass wm = listOfWater.get(id);
			System.out.println("Choose list of treatmentPlant");
			List<TreatmentPlant> ltp = jManager.getAllTreatmentPlantIndividuals();
			for (int i = 0; i < ltp.size(); ++i) {
				System.out.println(i + ". " + ltp.get(i).toString());
			}
			System.out.println("Choose number of treatmentPlant");
			int id1 = sc.nextInt();
			TreatmentPlant tp = ltp.get(id1);
			WaterMass res = null;
			Method method = processes.getClass().getMethod(nameFunction, TreatmentPlant.class, WaterMass.class);
			res = (WaterMass) method.invoke(processes, tp, wm);
			if (res != null) {
				jManager.addWatermassIndividual(res);
				System.out.println("New Water Mass Purified : " + res.toString());
			} else
				System.out.println("Not Purified");
		}
	}

	public void timeToPurifyWatermass() throws Exception {
		String nameFunction = getFunctionOfProcess("TimeToReducePollutant");
		if (nameFunction != null) {
			System.out.println("Name of Function : " + nameFunction);
			System.out.println("Choose level of DBO");
			double dbo = sc.nextDouble();
			System.out.println("Choose level of DQO");
			double dqo = sc.nextDouble();
			System.out.println("-Choose list of Watermasses-");
			List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
			for (int i = 0; i < listOfWater.size(); i++)
				System.out.println(i + ". " + listOfWater.get(i).toString());
			System.out.println("Choose number of waterMass");
			int id = sc.nextInt();
			WaterMass wm = listOfWater.get(id);
			System.out.println("Choose list of treatmentPlant");
			List<TreatmentPlant> ltp = jManager.getAllTreatmentPlantIndividuals();
			for (int i = 0; i < ltp.size(); ++i) {
				System.out.println(i + ". " + ltp.get(i).toString());
			}
			System.out.println("Choose number of treatmentPlant");
			int id1 = sc.nextInt();
			TreatmentPlant tp = ltp.get(id1);
			WaterMass obj = new WaterMass(wm.volume, dbo, dqo);
			WaterMass res = null;
			MyTime duracion = new MyTime(0);
			Method method = processes.getClass().getMethod(nameFunction, TreatmentPlant.class, WaterMass.class,
					WaterMass.class, MyTime.class);
			res = (WaterMass) method.invoke(processes, tp, wm, obj, duracion);
			if (res != null) {
				jManager.addWatermassIndividual(res);
				System.out.println("WaterMass objective :" + obj.toString());
				System.out.println("New Water Mass Purified : " + res.toString());
				System.out.println("Take " + duracion.getTime() + " hours");
			} else
				System.out.println("Not Purified");
		}
	}

	public void efficienfyOfPlant() throws Exception {
		String nameFunction = getFunctionOfProcess("TreatmentPlantEfficiency");
		if (nameFunction != null) {
			System.out.println("Name of Function : " + nameFunction);
			// Chose de Treatment Plant paramater
			System.out.println("-Choose list of treatmentPlant-");
			List<TreatmentPlant> trmtsPlants = jManager.getAllTreatmentPlantIndividuals();
			if (trmtsPlants.isEmpty()) {
				System.out.println("No Treatment Plants");
				return;
			}
			for (int i = 0; i < trmtsPlants.size(); ++i)
				System.out.println(i + ". " + trmtsPlants.get(i).toString());
			System.out.print("Choose number of treatmentPlant : ");
			int idTP = sc.nextInt();
			sc.nextLine();
			TreatmentPlant tp = trmtsPlants.get(idTP);
			// parametros masas de agua
			System.out.println("-Chose list of Watermasses-");
			List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
			for (int i = 0; i < listOfWater.size(); i++)
				System.out.println(i + ". " + listOfWater.get(i).toString());
			System.out.print("List of ids [0 1 4 5 . . .] : ");
			String ids = sc.nextLine();
			String[] splited = ids.split("\\s+");
			List<WaterMass> parameters = new ArrayList<>();
			for (int i = 0; i < splited.length; i++) {
				int id = Integer.valueOf(splited[i]);
				parameters.add(listOfWater.get(id));
			}
			// effeciency of TP with paramaters as input watermasses
			Method method = processes.getClass().getMethod(nameFunction, TreatmentPlant.class, List.class);
			method.invoke(processes, tp, parameters);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("---------------------- Starting program ----------------------");
		System.out.println();
		Main main = new Main();
		main.start();
		System.out.println("--------------------- Program terminated ---------------------");
	}
}