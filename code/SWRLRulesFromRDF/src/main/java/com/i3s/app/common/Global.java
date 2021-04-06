/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i3s.app.common;

import com.i3s.app.dataprocessing.assertion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;

/**
 * This class manage all the declaration of the statics variables of the project.
 */
public class Global 
{
	
	/**
	 * Output directory : where all files produced by module will be written
	 */
	public static final String OUTPUT_PATH = System.getProperty("user.dir").replace("\\", "/") + "/SWRLRulesFromRDF/";
	
	/**
     * The name of the file in which the rules will be written.
     */
    public static String outputName;   
    
    /**
     * Initialize a output folder which contains all generated files
     */
    public static String outputDir;
    
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
    public static Set<String> allIndividuals = new LinkedHashSet<String>();
    
    /**
     * The SPARQL Endpoint to make our requests
     */
    public static String SPARQL_ENDPOINT;
    
    /**
     * 
     */
    public static final int FR_THR = 1;
    
    /**
     * Choose the maximum length of the generated pattern in the first population.
     */
    public static final int MAX_LENGTH_PATTERN = 10;
    
    /**
     * Choose the maximum number of patterns in a population.
     */
    public static int MAX_SIZE_POPULATION = 2000;    
    
    /**
     * Choose the population rate used to compute the crossover size
     */
    public static double CROSSOVER_RATE = 0.25;
    
    /** 
     * Choose the population rate used to compute the mutation size
     */
    public static double MUTATION_RATE = 0.25;
    
    /**
     * Choose the maximum number of executions for module
     */
    public static int NB_EXECUTIONS = 2;
    
    /**
     * Choose the maximum number of generations.
     */
    public static int MAX_SIZE_GENERATION = 200;    
    
    /**
     * Choose the number of times to crossover between the patterns of a population.
     */
    public static int CROSSOVER_SIZE = (int) Math.round(CROSSOVER_RATE * MAX_SIZE_POPULATION);
    
    /**
     * Choose the number of times to mutation between the patterns of a population.
     */
    public static int MUTATION_SIZE = (int) Math.round(MUTATION_RATE * MAX_SIZE_POPULATION);
    
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
 
}
