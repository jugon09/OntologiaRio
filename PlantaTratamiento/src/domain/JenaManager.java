package domain;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;

public class JenaManager {

	private final String ONTOLOGY_PATH;
	private final String NAMING_CONTEXT;

	private OntModel mModel;
	private OntDocumentManager mOntDM;

	public JenaManager(String pathFile, String namingContext) {
		ONTOLOGY_PATH = pathFile;
		NAMING_CONTEXT = namingContext;
	}

	public void loadOntology() {
		System.out.println("Loading Ontology . . .");
		mModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
		mOntDM = mModel.getDocumentManager();
		mOntDM.addAltEntry(NAMING_CONTEXT, "file:" + ONTOLOGY_PATH);
		mModel.read(NAMING_CONTEXT);
		System.out.println("Ontology Loaded succesfully");
	}

	public void releaseOntology() throws FileNotFoundException {
		System.out.println("Releasing Ontology . . .");
		if (!mModel.isClosed()) {
			mModel.write(new FileOutputStream(ONTOLOGY_PATH));
			mModel.close();
		}
	}

	public List<String> findAllProcessesOf(String nameProcess) {
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + "PREFIX prac: <" + NAMING_CONTEXT + "> "
				+ "SELECT ?functionName " + "WHERE { " + "?s a prac:" + nameProcess + " . "
				+ "?s prac:hasCode ?functionName }";
		Query query = QueryFactory.create(queryString);
		try (QueryExecution qe = QueryExecutionFactory.create(query, mModel)) {
			ResultSet results = qe.execSelect();
			results = ResultSetFactory.copyResults(results);
			List<String> functions = new ArrayList<>();
			while (results.hasNext()) {
				QuerySolution sol = results.next();
				RDFNode n = sol.get("functionName");
				if (n.isLiteral())
					functions.add(n.asLiteral().toString());
			}
			qe.close();
			return functions;
		}
	}

	/************************ WaterMass ************************/

	public List<WaterMass> getAllWatermassIndividuals() {
		List<WaterMass> result = new ArrayList<WaterMass>();
		OntClass watermassClass = mModel.getOntClass(NAMING_CONTEXT + "WaterMass");
		for (Iterator<Individual> i = mModel.listIndividuals(watermassClass); i.hasNext();) {
			Individual ind = i.next();
			result.add(getWatermassFromIndividual(ind));
		}
		return result;
	}

	private WaterMass getWatermassFromIndividual(Individual water) {
		// Volum
		Property propertyVolum = mModel.getProperty(NAMING_CONTEXT + "hasVolume");
		RDFNode nodeVolume = water.getPropertyValue(propertyVolum);
		double volum = 0;
		if (nodeVolume != null && nodeVolume.isLiteral())
			volum = nodeVolume.asLiteral().getDouble();
		// DBO
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "hasDBO");
		RDFNode nodeDBO = water.getPropertyValue(propertyDBO);
		double dbo = 0;
		if (nodeDBO != null && nodeDBO.isLiteral())
			dbo = nodeDBO.asLiteral().getDouble();
		// DBQ
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "hasDQO");
		RDFNode nodeDQO = water.getPropertyValue(propertyDQO);
		double dqo = 0;
		if (nodeDQO != null && nodeDQO.isLiteral())
			dqo = nodeDQO.asLiteral().getDouble();
		// SST
		Property propertySST = mModel.getProperty(NAMING_CONTEXT + "hasSST");
		RDFNode nodeSST = water.getPropertyValue(propertySST);
		double sst = 0;
		if (nodeSST != null && nodeSST.isLiteral())
			sst = nodeSST.asLiteral().getDouble();
		// Nitrates
		Property propertyNit = mModel.getProperty(NAMING_CONTEXT + "hasNitrates");
		RDFNode nodeNit = water.getPropertyValue(propertyNit);
		double nit = 0;
		if (nodeNit != null && nodeNit.isLiteral())
			nit = nodeNit.asLiteral().getDouble();
		// Phosphates
		Property propertyPho = mModel.getProperty(NAMING_CONTEXT + "hasPhosphates");
		RDFNode nodePho = water.getPropertyValue(propertyPho);
		double pho = 0;
		if (nodePho != null && nodePho.isLiteral())
			pho = nodePho.asLiteral().getDouble();
		// WaterMass
		return new WaterMass(volum, dbo, dqo, sst, nit, pho);
	}

	public void addWatermassIndividual(WaterMass w) {
		OntClass watermassClass = mModel.getOntClass(NAMING_CONTEXT + "WaterMass");
		Individual particularWatermass = watermassClass
				.createIndividual(NAMING_CONTEXT + "watermass_" + UUID.randomUUID());
		Property propertyVolum = mModel.getProperty(NAMING_CONTEXT + "hasVolume");
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "hasDBO");
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "hasDQO");
		Property propertySST = mModel.getProperty(NAMING_CONTEXT + "hasSST");
		Property propertyNIT = mModel.getProperty(NAMING_CONTEXT + "hasNitrates");
		Property propertyPHO = mModel.getProperty(NAMING_CONTEXT + "hasPhosphates");
		Literal volum = mModel.createTypedLiteral(new Float(w.volume));
		Literal DBO = mModel.createTypedLiteral(new Float(w.DBO));
		Literal DQO = mModel.createTypedLiteral(new Float(w.DQO));
		Literal SST = mModel.createTypedLiteral(new Float(w.SST));
		Literal NIT = mModel.createTypedLiteral(new Float(w.Nitrates));
		Literal PHO = mModel.createTypedLiteral(new Float(w.Phosphates));
		particularWatermass.addLiteral(propertyVolum, volum);
		particularWatermass.addLiteral(propertyDBO, DBO);
		particularWatermass.addLiteral(propertyDQO, DQO);
		particularWatermass.addLiteral(propertySST, SST);
		particularWatermass.addLiteral(propertyNIT, NIT);
		particularWatermass.addLiteral(propertyPHO, PHO);
	}

	/************************ Factory ************************/

	public List<Factory> getAllFactoryIndividuals() {
		List<Factory> result = new ArrayList<Factory>();
		OntClass watermassClass = mModel.getOntClass(NAMING_CONTEXT + "Factory");
		for (Iterator<Individual> i = mModel.listIndividuals(watermassClass); i.hasNext();) {
			Individual ind = i.next();
			result.add(getFactoryFromIndividual(ind));
		}
		return result;
	}

	private Factory getFactoryFromIndividual(Individual factory) {
		// Industry
		ObjectProperty propertyBelongTo = mModel.getObjectProperty(NAMING_CONTEXT + "BelongTo");
		Individual ind = factory.getProperty(propertyBelongTo).getObject().as(Individual.class);
		Industry industry = getIndustryFromIndividual(ind);
		// Capacity
		ObjectProperty propertyHas = mModel.getObjectProperty(NAMING_CONTEXT + "has");

		Individual tank = factory.getProperty(propertyHas).getObject().as(Individual.class);
		Property propertyCapacity = mModel.getProperty(NAMING_CONTEXT + "hasCapacity");
		RDFNode nodeCapacity = tank.getPropertyValue(propertyCapacity);
		double capacity = nodeCapacity.asLiteral().getDouble();
		return new Factory(capacity, industry);
	}

	/************************ Industry ************************/

	private Industry getIndustryFromIndividual(Individual industry) {
		// DBO
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "produceDBO");
		RDFNode nodeDBO = industry.getPropertyValue(propertyDBO);
		double dbo = 0;
		if (nodeDBO != null && nodeDBO.isLiteral())
			dbo = nodeDBO.asLiteral().getDouble();
		// DBQ
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "produceDQO");
		RDFNode nodeDQO = industry.getPropertyValue(propertyDQO);
		double dqo = 0;
		if (nodeDQO != null && nodeDQO.isLiteral())
			dqo = nodeDQO.asLiteral().getDouble();
		// SST
		Property propertySST = mModel.getProperty(NAMING_CONTEXT + "produceSST");
		RDFNode nodeSST = industry.getPropertyValue(propertySST);
		double sst = 0;
		if (nodeSST != null && nodeSST.isLiteral())
			sst = nodeSST.asLiteral().getDouble();
		// Nitrates
		Property propertyNit = mModel.getProperty(NAMING_CONTEXT + "produceNitrates");
		RDFNode nodeNit = industry.getPropertyValue(propertyNit);
		double nit = 0;
		if (nodeNit != null && nodeNit.isLiteral())
			nit = nodeNit.asLiteral().getDouble();
		// Phosphates
		Property propertyPho = mModel.getProperty(NAMING_CONTEXT + "producePhosphates");
		RDFNode nodePho = industry.getPropertyValue(propertyPho);
		double pho = 0;
		if (nodePho != null && nodePho.isLiteral())
			pho = nodePho.asLiteral().getDouble();
		// Industry
		return new Industry(dbo, dqo, sst, nit, pho);
	}

	/************************ TreatmentPlant ************************/

	public List<TreatmentPlant> getAllTreatmentPlantIndividuals() {
		List<TreatmentPlant> res = new ArrayList<TreatmentPlant>();
		OntClass treatmentplantClass = mModel.getOntClass(NAMING_CONTEXT + "TreatmentPlant");
		for (Iterator<Individual> i = mModel.listIndividuals(treatmentplantClass); i.hasNext();) {
			Individual tp = i.next();
			res.add(getTreatmentPlantFromIndividual(tp));
		}
		return res;
	}

	public TreatmentPlant getTreatmentPlantFromIndividual(Individual tp) {
		TreatmentPlant res = null;
		ObjectProperty propertyApply = mModel.getObjectProperty(NAMING_CONTEXT + "applies");
		ArrayList<Treatment> trs = new ArrayList<Treatment>();
		for (NodeIterator it = tp.listPropertyValues(propertyApply); it.hasNext();) {
			RDFNode node = it.next();
			Individual ind = node.as(Individual.class);
			trs.add(getTreatmentFromTreatmentPlant(ind));
		}
		ArrayList<Tank> tqs = new ArrayList<Tank>();
		ObjectProperty propertyHas = mModel.getObjectProperty(NAMING_CONTEXT + "has");
		for (NodeIterator it = tp.listPropertyValues(propertyHas); it.hasNext();) {
			RDFNode node = it.next();
			Individual ind = node.as(Individual.class);
			tqs.add(getTankFromTreatmentPlant(ind));
		}
		res = new TreatmentPlant(trs, tqs);
		return res;
	}

	/************************ Tank ************************/

	public Tank getTankFromTreatmentPlant(Individual tank) {
		Property propertyCapacity = mModel.getProperty(NAMING_CONTEXT + "hasCapacity");
		RDFNode nodeCapacity = tank.getPropertyValue(propertyCapacity);
		double cap = nodeCapacity.asLiteral().getDouble();
		return new Tank(cap);
	}

	/************************ Treatment ************************/

	public Treatment getTreatmentFromTreatmentPlant(Individual tp) {
		Property propertyTime = mModel.getProperty(NAMING_CONTEXT + "takesHours");
		RDFNode nodeTime = tp.getPropertyValue(propertyTime);
		int tiempo = nodeTime.asLiteral().getInt();
		// DBO
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "reduceDBO");
		RDFNode nodeDBO = tp.getPropertyValue(propertyDBO);
		double dbo = 0;
		if (nodeDBO != null && nodeDBO.isLiteral())
			dbo = nodeDBO.asLiteral().getDouble();
		// DBQ
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "reduceDQO");
		RDFNode nodeDQO = tp.getPropertyValue(propertyDQO);
		double dqo = 0;
		if (nodeDQO != null && nodeDQO.isLiteral())
			dqo = nodeDQO.asLiteral().getDouble();
		// SST
		Property propertySST = mModel.getProperty(NAMING_CONTEXT + "reduceSST");
		RDFNode nodeSST = tp.getPropertyValue(propertySST);
		double sst = 0;
		if (nodeSST != null && nodeSST.isLiteral())
			sst = nodeSST.asLiteral().getDouble();
		// Nitrates
		Property propertyNit = mModel.getProperty(NAMING_CONTEXT + "reduceNitrates");
		RDFNode nodeNit = tp.getPropertyValue(propertyNit);
		double nit = 0;
		if (nodeNit != null && nodeNit.isLiteral())
			nit = nodeNit.asLiteral().getDouble();
		// Phosphates
		Property propertyPho = mModel.getProperty(NAMING_CONTEXT + "reducePhosphates");
		RDFNode nodePho = tp.getPropertyValue(propertyPho);
		double pho = 0;
		if (nodePho != null && nodePho.isLiteral())
			pho = nodePho.asLiteral().getDouble();
		return new Treatment(tiempo, dbo, dqo, sst, nit, pho);
	}

	/************************ Normatives ************************/

	public List<Normative> getAllNormativeIndividuals() {
		List<Normative> result = new ArrayList<Normative>();
		OntClass normativeClass = mModel.getOntClass(NAMING_CONTEXT + "Normative");
		for (Iterator<Individual> i = mModel.listIndividuals(normativeClass); i.hasNext();) {
			Individual ind = i.next();
			result.add(getNormativeFromIndividual(ind));
		}
		return result;
	}

	private Normative getNormativeFromIndividual(Individual normative) {
		// DBO
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "hasDBOLimit");
		RDFNode nodeDBO = normative.getPropertyValue(propertyDBO);
		double dbo = 0;
		if (nodeDBO != null && nodeDBO.isLiteral())
			dbo = nodeDBO.asLiteral().getDouble();
		// DBQ
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "hasDQOLimit");
		RDFNode nodeDQO = normative.getPropertyValue(propertyDQO);
		double dqo = 0;
		if (nodeDQO != null && nodeDQO.isLiteral())
			dqo = nodeDQO.asLiteral().getDouble();
		// SST
		Property propertySST = mModel.getProperty(NAMING_CONTEXT + "hasSSTLimit");
		RDFNode nodeSST = normative.getPropertyValue(propertySST);
		double sst = 0;
		if (nodeSST != null && nodeSST.isLiteral())
			sst = nodeSST.asLiteral().getDouble();
		// Nitrates
		Property propertyNit = mModel.getProperty(NAMING_CONTEXT + "hasNitratesLimit");
		RDFNode nodeNit = normative.getPropertyValue(propertyNit);
		double nit = 0;
		if (nodeNit != null && nodeNit.isLiteral())
			nit = nodeNit.asLiteral().getDouble();
		// Phosphates
		Property propertyPho = mModel.getProperty(NAMING_CONTEXT + "hasPhosphatesLimit");
		RDFNode nodePho = normative.getPropertyValue(propertyPho);
		double pho = 0;
		if (nodePho != null && nodePho.isLiteral())
			pho = nodePho.asLiteral().getDouble();
		// Name
		Property propertyName = mModel.getProperty(NAMING_CONTEXT + "hasName");
		RDFNode nodeName = normative.getPropertyValue(propertyName);
		String name = "Unknow";
		if (nodeName != null && nodeName.isLiteral())
			name = nodeName.asLiteral().getString();
		return new Normative(name, dbo, dqo, sst, nit, pho);
	}

}