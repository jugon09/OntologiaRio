package domain;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final String ONTO_PATH = "../Ontologia.owl";
	private static final String NAMING_CONTEXT = "http://www.semanticweb.org/camilo/ontologies/2016/4/ontologiaRio#";

	private Scanner sc;
	private JenaManager jManager;
	private Processes processes;

	public void start() {
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
				System.out.println(" -- Exit --");
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
			System.out.print("\nPress Enter To Continue");
			sc.nextLine();
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

	public void generateWatermass() {
		System.out.println();

	}

	public void mergeWatermass() {
		try {
			String nameFunction = getFunctionOfProcess("MergeWater");
			if (nameFunction != null) {
				System.out.println("Name of Function : " + nameFunction);
				// parametros masas de agua
				System.out.println("-Chose list of Watermasses-");
				List<WaterMass> listOfWater = jManager.getAllWatermassIndividuals();
				for (int i = 0; i < listOfWater.size(); i++)
					System.out.println(i + ". " + listOfWater.get(i).toString());
				sc.nextLine();
				//
				System.out.print("List of ids [0 1 4 5 . . .] : ");
				String ids = sc.nextLine();
				String[] splited = ids.split("\\s+");
				List<WaterMass> parameters = new ArrayList<>();
				for (int i = 0; i < splited.length; i++) {
					int id = Integer.valueOf(splited[i]);
					parameters.add(listOfWater.get(id));
				}
				// merge list of watermass
				Method method = processes.getClass().getMethod(nameFunction, List.class);
				WaterMass w3 = (WaterMass) method.invoke(processes, parameters);
				if (w3 != null) {
					jManager.addWatermassIndividual(w3);
					System.out.println("New Water Mass Merged : " + w3.toString());
				} else
					System.out.println("Not Merged");
			}
		} catch (Exception e) {
			System.out.print("Error : ");
			e.printStackTrace();
		}
	}

	public void classificationOfWatermass() {
		System.out.println();
	}

	public void purificationOfWatermass() {
		System.out.println();
	}

	public void timeToPurifyWatermass() {
		System.out.println();
	}

	public void efficienfyOfPlant() {
		System.out.println();
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("---------------------- Starting program ----------------------");
		System.out.println();
		Main main = new Main();
		main.start();
		System.out.println("--------------------- Program terminated ---------------------");
	}
}