package domain;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.jena.ontology.Individual;
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
	}

	public void releaseOntology() throws FileNotFoundException {
		System.out.println("Releasing Ontology . . .");
		if (!mModel.isClosed()) {
			mModel.write(new FileOutputStream(ONTOLOGY_PATH));
			mModel.close();
		}
	}

	public WaterMass getWatermassIndividual(String URI) {
		return getWatermassFromIndividual(mModel.getIndividual(URI));
	}

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
		double volum = nodeVolume.asLiteral().getDouble();
		// DBO
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "hasDBO");
		RDFNode nodeDBO = water.getPropertyValue(propertyDBO);
		double dbo = nodeDBO.asLiteral().getDouble();
		// DBQ
		/*
		 * Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "hasDQO");
		 * RDFNode nodeDQO = water.getPropertyValue(propertyDQO); double dqo =
		 * nodeDQO.asLiteral().getDouble();
		 */
		// WaterMass
		return new WaterMass(volum, dbo, 0);
	}

	public void addWatermassIndividual(WaterMass w) {
		OntClass watermassClass = mModel.getOntClass(NAMING_CONTEXT + "WaterMass");
		Individual particularWatermass = watermassClass
				.createIndividual(NAMING_CONTEXT + "watermass_" + UUID.randomUUID());
		Property propertyVolum = mModel.getProperty(NAMING_CONTEXT + "hasVolume");
		Property propertyDBO = mModel.getProperty(NAMING_CONTEXT + "hasDBO");
		Property propertyDQO = mModel.getProperty(NAMING_CONTEXT + "hasDQO");
		Literal volum = mModel.createTypedLiteral(new Float(w.volume));
		Literal DBO = mModel.createTypedLiteral(new Float(w.DBO));
		Literal DQO = mModel.createTypedLiteral(new Float(w.DQO));
		particularWatermass.addLiteral(propertyVolum, volum);
		particularWatermass.addLiteral(propertyDBO, DBO);
		particularWatermass.addLiteral(propertyDQO, DQO);
	}

	public String executeQuery() {
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX prac: <http://www.semanticweb.org/camilo/ontologies/2016/4/ontologiaRio#> "
				+ "SELECT ?functionName " + "WHERE { " + "?s a prac:Merge_water ." + "?s prac:hasCode ?functionName }";
		Query query = QueryFactory.create(queryString);
		String nameFunction = "";
		try (QueryExecution qe = QueryExecutionFactory.create(query, mModel)) {
			ResultSet results = qe.execSelect();
			results = ResultSetFactory.copyResults(results);
			while (results.hasNext()) {
				QuerySolution sol = results.next();
				RDFNode n = sol.get("functionName");
				if (n.isLiteral()) {
					nameFunction = n.asLiteral().toString();
					break;
				}
			}
			qe.close();
		}
		return nameFunction;
	}

}