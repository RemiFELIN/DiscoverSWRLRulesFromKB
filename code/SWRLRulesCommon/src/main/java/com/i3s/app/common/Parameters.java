package com.i3s.app.common;

/**
 * list of the different options available for the project tools
 * @author remif
 *
 */
public class Parameters {

	public static final String FILE = "file";
	public static final String FILE_DESCRIPTION = "path (relative or absolute) of file to test (*.txt for DiscoverSWRLRulesFromRDF, *.owl for DiscoverSWRLRulesFromOntology)";
	
	public static final String PREFIX_KB_URL = "pkbu";
	public static final String PREFIX_KB_URL_FULL = "prefix-knowledge-base";
	public static final String PREFIX_KB_URL_DESCRIPTION = "Prefix URL of the knowledge base to be analyze (ex: http://www.biopax.org/examples/glycolysis#)";
	
	public static final String SPARQL_ENDPOINT = "spqu";
	public static final String SPARQL_ENDPOINT_FULL = "sparql-endpoint";
	public static final String SPARQL_ENDPOINT_DESCRIPTION = "SPARQL endpoint to make our requests (ex: http://covidontheweb.inria.fr/sparql)";
	
	public static final String NUMBER_OF_EXECUTION = "nexe";
	public static final String NUMBER_OF_EXECUTION_FULL = "n-execution";
	public static final String NUMBER_OF_EXECUTION_DESCRIPTION = "the number of executions. (by default: 2)";
	
	public static final String NUMBER_OF_GENERATION = "ngen";
	public static final String NUMBER_OF_GENERATION_FULL = "n-generation";
	public static final String NUMBER_OF_GENERATION_DESCRIPTION = "the number of generations. (by default: 200)";
	
	public static final String POPULATION_SIZE = "posz";
	public static final String POPULATION_SIZE_FULL = "pop-size";
	public static final String POPULATION_SIZE_DESCRIPTION = "the maximum number of patterns in a population. (by default: 2000)";
	
	public static final String CROSSOVER_RATE = "crrt";
	public static final String CROSSOVER_RATE_FULL = "crossover-rate";
	public static final String CROSSOVER_RATE_DESCRIPTION = "the population rate used to compute the crossover size. (by default: 0.25)";
	
	public static final String MUTATION_RATE = "mtrt";
	public static final String MUTATION_RATE_FULL = "mutation-rate";
	public static final String MUTATION_RATE_DESCRIPTION = "the population rate used to compute the mutation size. (by default: 0.25)";
	
	public static final String MUTATION_THRESHOLD = "mtth";
	public static final String MUTATION_THRESHOLD_FULL = "mutation-threshold";
	public static final String MUTATION_THRESHOLD_DESCRIPTION = "the threshold thanks to which the specialization or the generalization is made. (by default: 0.2)";
	
	public static final String CHECK_CONSISTENCY_FILE = "cstf";
	public static final String CHECK_CONSISTENCY_FILE_FULL = "check-consistence-file";
	public static final String CHECK_CONSISTENCY_FILE_DESCRIPTION = "path (relative or absolute) of the full ontology to be loaded. (*.owl file)";

}
