package com.i3s.app.common;

/**
 * list of the different options available for the project tools
 * @author remif
 *
 */
public class Parameters {

	public static final String FILE = "file";
	public static final String FILE_DESCRIPTION = "path of file to test (.txt for DiscoverSWRLRulesFromRDF, .owl for DiscoverSWRLRulesFromOntology)";
	
	public static final String URL = "url";
	public static final String URL_DESCRIPTION = "It can be the sparql endpoint to make our requests (ex: http://covidontheweb.inria.fr/sparql) or URL of the knowledge base to be analyze (ex: http://www.biopax.org/examples/glycolysis#)";
	
	public static final String NUMBER_OF_EXECUTIONS = "nexe";
	public static final String NUMBER_OF_EXECUTIONS_FULL = "n-execution";
	public static final String NUMBER_OF_EXECUTIONS_DESCRIPTION = "the number of executions. (by default: 2)";
	
	public static final String NUMBER_OF_GENERATION = "ngen";
	public static final String NUMBER_OF_GENERATION_FULL = "n-generation";
	public static final String NUMBER_OF_GENERATION_DESCRIPTION = "the number of generations. (by default: 200)";
	
	public static final String CROSSOVER_SIZE = "crsz";
	public static final String CROSSOVER_SIZE_FULL = "crossover-size";
	public static final String CROSSOVER_SIZE_DESCRIPTION = "the number of times to crossover between the patterns of a population. (by default: 500)";
	
	public static final String MUTATION_SIZE = "mtsz";
	public static final String MUTATION_SIZE_FULL = "mutation-size";
	public static final String MUTATION_SIZE_DESCRIPTION = "the number of times to mutation between the patterns of a population. (by default: 500)";
	
	public static final String MUTATION_THRESHOLD = "mtth";
	public static final String MUTATION_THRESHOLD_FULL = "mutation-threshold";
	public static final String MUTATION_THRESHOLD_DESCRIPTION = "the threshold thanks to which the specialization or the generalization is made. (by default: 0.2)";
	
	public static final String CHECK_CONSISTENCE_FILE = "cstf";
	public static final String CHECK_CONSISTENCE_FILE_FULL = "check-consistence-file";
	public static final String CHECK_CONSISTENCE_FILE_DESCRIPTION = "absolute path of the full ontology to be loaded (.owl file)";

}
