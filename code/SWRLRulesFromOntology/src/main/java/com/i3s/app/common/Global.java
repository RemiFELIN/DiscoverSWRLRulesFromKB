/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i3s.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.model.IRI;
import com.i3s.app.consistency.pattern.*;

import com.i3s.app.dataprocessing.assertion.ConceptAssertion;
import com.i3s.app.dataprocessing.assertion.RoleAssertion;

/**
 * This class manage all the declaration of the statics variables of the project.
 * @author tdminh
 */
public class Global 
{

    /**
     * The URL of the knowledge base to be analyze.
     */
    public static String BASE_URL;
    
    /**
	 * Output directory : where all files produced by module will be written
	 */
	public static final String OUTPUT_PATH = System.getProperty("user.dir").replace("\\", "/") + "/SWRLRulesFromOntology/";
	
	/**
     * The name of the file in which the rules will be written.
     */
    public static String outputName;   
    
    /**
     * Initialize a output folder which contains all generated files
     */
    public static String outputDir;
    
    /**
     * The directory of the stratified knowledge base
     */ 
    public static String file_name_stratified;
    
    /**
     * From the directory of the stratified KB, the IRI is created using the create method of the owl api.
     */
    public static IRI IRI_INPUT_STRATIFIED;
    
    /**
     * The name of the file in which the rules will be written.
     */
    // public static final String OUTPUT_PATTERNS_IN_FILE = "Covid19";    
    
    /**
     * The reasoner used.
     */
    public static final String TYPE_OF_REASONER = "Hermit";
    
    /**
     * Specifies if the direct instances should be retrieved (true), 
     * or if all instances should be retrieved ( false).
     */ 
    public static final boolean FILTER_WITH_INDIVIDUAL = false;
    
    /**
     * ArrayList which contains all the concepts assertions in the KB stratified.
     * Each concept assertion is composed by the IRI of the class and the set of instances of this class.
     */
    public static ArrayList<ConceptAssertion> allFrequentConceptsStratified = new ArrayList<ConceptAssertion>();
    
    /**
     * ArrayList which contains all the role assertions in the KB stratified.
     * Each role assertion is composed by the IRI of the property and the set of instances of the domain and range of it.
     */
    public static ArrayList<RoleAssertion> allFrequentRolesStratified = new ArrayList<RoleAssertion>();
    
    /**
     * Initialization of the array containing the IRIs of all the classes in the KB stratified.
     */
    public static ArrayList<IRI> allIRIFrequentConceptsStratified = new ArrayList<IRI>();
    
    /**
     * Initialization of the array containing the IRIs of all the properties in the KB stratified.
     */
    public static ArrayList<IRI> allIRIFrequentRolesStratified = new ArrayList<IRI>();
    
    /**
     * Initialization of the HashMap containing the IRIs of all the classes as keys and all their superclasses as values.
     */
    public static Map<IRI, Set<IRI>> conceptIsSubsumedByConcepts = new HashMap<IRI, Set<IRI>>();
    
    /**
     * Initialization of the HashMap containing the IRIs of all the classes as keys and all their subclasses as values.
     */
    public static Map<IRI, Set<IRI>> conceptSubsumsConcepts = new HashMap<IRI, Set<IRI>>();
    
    /**
     * Initialization of the HashMap containing the IRIs of all the properties as keys and all their superproperties as values.
     */
    public static Map<IRI, Set<IRI>> roleIsSubPropertyOfRoles = new HashMap<IRI, Set<IRI>>();
    
    /**
     * Initialization of the HashMap containing the IRIs of all the properties as keys and all their subproperties as values.
     */
    public static Map<IRI, Set<IRI>> roleIsSuperPropertyOfRoles = new HashMap<IRI, Set<IRI>>();;
    
    /**
     * Initialization of the HashMap containing the IRIs of all the properties as keys and all their class domain as values.
     */
    public static Map<IRI, Set<IRI>> conceptsDomainOfRole = new HashMap<IRI, Set<IRI>>();
    
    /**
     * Initialization of the HashMap containing the IRIs of all the properties as keys and all their class range as values.
     */
    public static Map<IRI, Set<IRI>> conceptsRangeOfRole = new HashMap<IRI, Set<IRI>>();;
    
    /**
     * Initialize the set of all the individuals.
     */
    public static Set<String> allIndividuals;
    
    /**
     * 
     */
    public static final int FR_THR = 1;
    
    /**
     * Choose the maximum length of the generated pattern in the first population.
     */
    public static final int MAX_LENGTH_PATTERN = 10;
    
    /**
     * Choose the maximum number of generations.
     */
    public static int MAX_SIZE_GENERATION = 200;    
    
    /**
     * Choose the maximum number of patterns in a population.
     */
    public static final int MAX_SIZE_POPULATION = 2000;
    
    /**
     * Choose the maximum number of executions for module
     */
    public static int NB_EXECUTIONS = 2;
    
    /**
     * Choose the number of times to crossover between the patterns of a population.
     */
    public static int CROSSOVER_SIZE = 500;
    
    /**
     * Choose the number of times to mutation between the patterns of a population.
     */
    public static int MUTATION_SIZE = 500;
    
    /**
     * Choose the threshold thanks to which the specialization or the generalization is made.
     */
    public static double MUTATION_THR = 0.2;
    
    /**
     * Initialize a random date.
     */
    public static MyRandom myRandom;
    
    /**
     * Choose the name of the generic variable of the classes in the head of the rules.
     */
    public static final String variableForHeadConcept = "?c";
    
    /**
     * Choose the name of the generic variable of the domain of the property in the head of the rules.
     */
    public static final String variableForDomainOfHeadRole = "?x";
    
    /**
     * Choose the name of the generic variable of the range of the property in the head of the rules.
     */
    public static final String variableForRangeOfHeadRole = "?y";
    
    /**
     * Choose the name of the variable if a new variable is needed.
     */
    public static final String variableForFresh = "?z";
    
    /**
     * Choose the name of the variable for the PCA metric.
     */
    public static final String variableForPCA= "?y_pca";
    
    /**
     * Choose the maximum number of loop in the crossover.
     */
    public static final int MAX_LOOP_FOR_CHECK = 1000;
    
    /********************************************************************* 
     * CHECK RULES CONSISTENCY : VARIABLES
     *********************************************************************/
    
    /**
	 * The full ontology to be loaded TODO
	 */
    // public static final String FILE_NAME_FULL = "file:///D:/Travail/MIAGE/Stage/repo/DiscoverSWRLRulesFromKB/Data_owl/Biopax_Full.owl";
    public static String FILE_NAME_FULL;
    
    /**
	 * The IRI of the full ontology
	 */
    public static IRI IRI_INPUT_FULL;
    
    /**
     * The list of rules to be checked
     */
    public static ArrayList<String> LIST_INPUT_PATTERNS_IN_FILE = new ArrayList<>();
    
    /**
   	 * The rules to be checked
   	 */    
    public static String INPUT_PATTERNS_IN_FILE;
    
    /**
   	 * The file with the final rules
   	 */   
    public static String OUTPUT_PATTERNS_IN_FILE;
    
    /**
	 * The maximum number of threads
	 */
    public static final int MAX_THREAD = 10;
    
    public static int NUMBER_OF_THREAD = 0;
    
    public static int THREAD_SLEEP = 0;
    
    public static int iNumberOfConsistent = 0;
    public static int iNumberOfInconsistent = 0;
    public static int iNumberOfError = 0;
    
    public static ArrayList<Pattern> listCheckedPatterns = null;
    public static ThreadCheckPattern[] arrThreadCheckPattern = new ThreadCheckPattern[Global.MAX_THREAD];
    
    public static ThreadCheckThread threadCheckThread = null;
    
    public static int iNumberOfThreadCompleted = 0;
    public static int iNumberOfThreadRunning = 0;
    
 
}
