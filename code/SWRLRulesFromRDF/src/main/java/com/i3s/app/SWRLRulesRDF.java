package com.i3s.app;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.semanticweb.owlapi.model.IRI;

import com.i3s.app.common.Global;
import com.i3s.app.common.MyRandom;
import com.i3s.app.common.Parameters;
import com.i3s.app.dataprocessing.ConceptProcessing;
import com.i3s.app.dataprocessing.RoleProcessing;
import com.i3s.app.geneticalgorithm.GeneticAlgorithm;
import com.i3s.app.geneticalgorithm.Population;
import com.i3s.app.output.OutputInformation;
import com.i3s.tools.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Duc Minh Tran; Valeria Bellusci and Rémi FELIN
 */
public class SWRLRulesRDF 
{   
	
	public static Logger logFile;
	public static String className = SWRLRulesRDF.class.getName();
	
	/**
	 * This method is used to populate the data structure containing all the main elements of the data-sets.
	 * All these elements are taken from the sparql endpoint, through query sparql.
	 * @throws Exception 
	 */
	public SWRLRulesRDF(String path) throws Exception
	{   
		Global.myRandom = new MyRandom(); 
		//Fills the array from a file which contains all the necessary graphs.
		ArrayList<String> graph = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(new FileReader(path)); 
		String iri; 
		while ((iri = br.readLine()) != null) {
			graph.add(iri); 
		}
		br.close();
		
		// SPARQL Query     
		//Populate the data structures about class
		try {
			//Output of the queries
			OutputInformation output = new OutputInformation(Global.outputDir + "/" + Global.outputName + "_RESULTSQueryClass.txt", logFile);
			logFile.log("INFO", className, "file " + OutputInformation.outputFile.getPath().replace("\\", "/") + " created ...");
			//Do the query for each graph in the array. 
			//If the use of a graph is not necessary, remove the for loop and the "FROM" clause from the query.
			for(String i : graph) {

				//The endpoint 
				// String sparqlEndpoint = "http://covidontheweb.inria.fr/sparql";
				ConceptProcessing q = new ConceptProcessing(logFile);

				//Query which takes all the classes which have individuals
				String classQuery = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						+ "SELECT DISTINCT ?class "
						+ "FROM <" + i + "> "
						+ "WHERE { ?ind a ?class } "; 
				logFile.log("INFO", className, "START QUERY : " + classQuery);
				q.queryClass(classQuery, Global.SPARQL_ENDPOINT);
				Thread.sleep(5000);

			}

			//Write in the output file
			OutputInformation.showTextln("Result of query concept individuals (iri of the class): ", false);
			OutputInformation.showFrequentConceptsStratified(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query concept subsumed by concepts: ", false);
			OutputInformation.showFrequentConceptsStratifiedAndSuperConcepts(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query concept which subsumes concepts: ", false);
			OutputInformation.showFrequentConceptsStratifiedAndSubConcepts(false);
			OutputInformation.showTextln(" ", false);

			output.closeFile(); 
		}
		catch (HttpException ex) {
			Thread.sleep(10000);
		} 

		//Populate the data structures about property
		try {

			// String sparqlEndpoint = "http://covidontheweb.inria.fr/sparql";
			OutputInformation outputProp = new OutputInformation(Global.outputDir + "/" + Global.outputName + "_RESULTSQueryProperty.txt", logFile);

			//Do the query for each graph in the array. 
			//If the use of a graph is not necessary, remove the for loop and the "FROM" clause from the query.
			for(String i: graph) {
				RoleProcessing r = new RoleProcessing(logFile);
				String propertyIndividuals = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
						+ "SELECT DISTINCT  ?prop "
						+ "FROM <" + i + ">"
						+ "WHERE {?sub ?prop ?obj. FILTER ( ?prop != rdf:type ) }";

				r.queryPropertyIndividuals(propertyIndividuals, Global.SPARQL_ENDPOINT);
				Thread.sleep(5000);
			}

			////Write in the output file the results about the properties
			OutputInformation.showTextln("Result of query role individuals(iri of the role): ", false);
			OutputInformation.showFrequentRolesStratified(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query role subsumed by roles: ", false);
			OutputInformation.showFrequentRolesStratifiedAndSuperRoles(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query role which subsumes roles: ", false);
			OutputInformation.showFrequentRolesStratifiedAndSubRoles(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query domain classes: ", false);
			OutputInformation.showFrequentRolesStratifiedAndDomainsOfRoles(false);
			OutputInformation.showTextln(" ", false);

			OutputInformation.showTextln("Result of query range classes: ", false);
			OutputInformation.showFrequentRolesStratifiedAndRangesOfRoles(false);
			OutputInformation.showTextln(" ", false);

			outputProp.closeFile();

			//Write in the output file the results about the individuals of all the classes and properties
			OutputInformation outputInd = new OutputInformation(Global.outputDir + "/" + Global.outputName + "_RESULTSQueryInd.txt", logFile);
			OutputInformation.showTextln("Result of query all individuals: ", false);
			OutputInformation.showIndividualsStratified(false);
			OutputInformation.showTextln(" ", false);
			outputInd.closeFile();
		}
		catch (HttpException ex) {
			Thread.sleep(10000);
		}
	}

	/**
	 * @param strFileName the file in which the rules will be written.
	 * @return false if everything is okay.
	 */
	public boolean run(String strFileName)
	{
		OutputInformation output = new OutputInformation(strFileName, logFile);
		logFile.log("INFO", className, "file " + OutputInformation.outputFile.getPath() + " created...");

		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
		logFile.log("INFO", className, "Initialization of the initial population (creation of patterns using the atoms in the list of frequent concepts and frequent roles)");
		Population population = new Population(logFile);
		logFile.log("INFO", className, "Frequent concepts size: " + Global.allIRIFrequentConceptsStratified.size());
		logFile.log("INFO", className, "Frequent roles size: " + Global.allIRIFrequentRolesStratified.size());
		population.autoCreatePopulation();

		boolean blRunAgain = false;
		logFile.log("INFO", className, "Starting genetic algorithm...");
		
		for (int i = 1; i <= Global.MAX_SIZE_GENERATION; i++)
		{            
			try
			{
				logFile.log("INFO", className, "> Generation " + i + "/" + Global.MAX_SIZE_GENERATION);
				population = geneticAlgorithm.generateNewGenerationByCrossover(population);
				population.compute();
			} catch(java.lang.OutOfMemoryError e) {
				logFile.log("ERROR", className, e.toString());
				blRunAgain = true;
				break;
			}
		}

		if (!blRunAgain)
		{            
			logFile.log("INFO", className, "end genetic algorithm...");
			
			int count = 0;

			logFile.log("INFO", className, "Size of final population: " + population.getListIndividuals().size());
			logFile.log("INFO", className, "The number of times to crossover between the patterns of a population: " + Global.CROSSOVER_SIZE);
            logFile.log("INFO", className, "The number of times to mutation between the patterns of a population: " + Global.MUTATION_SIZE);
            
			int count2 = 0;
			for(int i = 0; i < population.getListIndividuals().size(); i++)
			{   
				if (population.getListIndividuals().get(i).getPatternComputation().getFitnessValue() > 0)                        
				{
					++count;

					OutputInformation.showText(count + ". ", false);
					OutputInformation.showPattern(population.getListIndividuals().get(i), i, false);
				}
				else {
					count2++;
				}

			}
			logFile.log("INFO", className, "Survived " + count + " patterns.");
			logFile.log("INFO", className, "Lost " + count2 + " patterns, because the fintess values were less than zero.");
		}

		output.closeFile();    

		return blRunAgain;
	}

	/**
     * Initialize all parameters needed to execute this tool
     * @param args the arguments given at execution
     * @return cmd an instance of the CommandLine if all parameters are avalaible, else null
     */
    public static CommandLine initParameters(String args[]) {

		Options options = new Options();
		
		Option paramFile = new Option(Parameters.FILE, true, Parameters.FILE_DESCRIPTION);
		Option paramUrl = new Option(Parameters.SPARQL_ENDPOINT, Parameters.SPARQL_ENDPOINT_FULL, true, Parameters.SPARQL_ENDPOINT_DESCRIPTION);
		Option paramNexe = new Option(Parameters.NUMBER_OF_EXECUTION, Parameters.NUMBER_OF_EXECUTION_FULL, true, Parameters.NUMBER_OF_EXECUTION_DESCRIPTION);
		Option paramNgen = new Option(Parameters.NUMBER_OF_GENERATION, Parameters.NUMBER_OF_GENERATION_FULL, true, Parameters.NUMBER_OF_GENERATION_DESCRIPTION);
		Option paramPosz = new Option(Parameters.POPULATION_SIZE, Parameters.POPULATION_SIZE_FULL, true, Parameters.POPULATION_SIZE_DESCRIPTION);
		Option paramCrrt = new Option(Parameters.CROSSOVER_RATE, Parameters.CROSSOVER_RATE_FULL, true, Parameters.CROSSOVER_RATE_DESCRIPTION);
		Option paramMtrt = new Option(Parameters.MUTATION_RATE, Parameters.MUTATION_RATE_FULL, true, Parameters.MUTATION_RATE_DESCRIPTION);
		Option paramMtth = new Option(Parameters.MUTATION_THRESHOLD, Parameters.MUTATION_THRESHOLD_FULL, true, Parameters.MUTATION_THRESHOLD_DESCRIPTION);
		
		paramFile.setRequired(true);
		paramUrl.setRequired(true);
		paramNexe.setRequired(false);
		paramNexe.setType(Integer.class);
		paramNgen.setRequired(false);
		paramNgen.setType(Integer.class);
		paramPosz.setRequired(false);
		paramPosz.setType(Integer.class);
		paramCrrt.setRequired(false);
		paramCrrt.setType(Integer.class);
		paramMtrt.setRequired(false);
		paramMtrt.setType(Integer.class);
		paramMtth.setRequired(false);
		paramMtth.setType(Double.class);
		
		options.addOption(paramFile);
		options.addOption(paramUrl);
		options.addOption(paramNexe);
		options.addOption(paramNgen);
		options.addOption(paramPosz);
		options.addOption(paramCrrt);
		options.addOption(paramMtrt);
		options.addOption(paramMtth);
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args);
		} catch(ParseException e) {
			System.out.println(Logger.getDate() + " [" + SWRLRulesRDF.className + "] ERROR : Parameters given are not corrects");
			formatter.printHelp(className, options);
            System.exit(1);
		}
		
		return cmd;
    }
	
	/**
	 * The entry point of the Genetic Algorithm application.
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception
	{
		
		CommandLine cmd = initParameters(args);
		
		// FILE
	    File file = new File(cmd.getOptionValue(Parameters.FILE));
		if(!file.exists()) {
			System.out.println(Logger.getDate() + " [" + SWRLRulesRDF.className + "] ERROR : file path is not correct: " + cmd.getOptionValue(Parameters.FILE));
			System.exit(1);
		}
		
		// URL
		try {
			// check the availability of URL
			(new URL(cmd.getOptionValue(Parameters.SPARQL_ENDPOINT))).openStream().close();
			// define value of URL
			Global.SPARQL_ENDPOINT = cmd.getOptionValue(Parameters.SPARQL_ENDPOINT);
		} catch (Exception e) {
			System.out.println(Logger.getDate() + " [" + className + "] ERROR : URL is not avalaible: " + cmd.getOptionValue(Parameters.SPARQL_ENDPOINT));
			System.exit(1);
		}
		
		// number of execution
		if(cmd.getOptionValue(Parameters.NUMBER_OF_EXECUTION) != null) {
			int value = Integer.parseInt(cmd.getOptionValue(Parameters.NUMBER_OF_EXECUTION));
			// check if the number of execution is greater than 0
			if(value > 0) {
				Global.NB_EXECUTIONS = value;
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Number of execution must be greater than 0: " + value);
				System.exit(1);
			}
		}
		
		// number of generation
		if(cmd.getOptionValue(Parameters.NUMBER_OF_GENERATION) != null) {
			int value = Integer.parseInt(cmd.getOptionValue(Parameters.NUMBER_OF_GENERATION));
			// check if the number of generation is greater than 0
			if(value > 0) {
				Global.MAX_SIZE_GENERATION = value;
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Number of generation must be greater than 0: " + value);
				System.exit(1);
			}
		}
			
		// population size
		if(cmd.getOptionValue(Parameters.POPULATION_SIZE) != null) {
			int value = Integer.parseInt(cmd.getOptionValue(Parameters.POPULATION_SIZE));
			// check if the population size is greater than 0
			if(value > 0) {
				Global.MAX_SIZE_POPULATION = value;
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Population size must be greater than 0: " + value);
				System.exit(1);
			}
		}
			
		// crossover rate
		if(cmd.getOptionValue(Parameters.CROSSOVER_RATE) != null) {
			Double value = Double.parseDouble(cmd.getOptionValue(Parameters.CROSSOVER_RATE));
			// test if the crossover rate is between 0 and 1
			if(value >= 0.0 && value <= 1.0) {
				Global.CROSSOVER_RATE = value;
				Global.CROSSOVER_SIZE = (int) Math.round(Global.CROSSOVER_RATE * Global.MAX_SIZE_POPULATION);
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Crossover rate must be included between 0 and 1 : " + value);
				System.exit(1);
			}
		}
		
		// mutation rate
		if(cmd.getOptionValue(Parameters.MUTATION_RATE) != null) {
			Double value = Double.parseDouble(cmd.getOptionValue(Parameters.MUTATION_RATE));
			// test if the mutation rate is between 0 and 1
			if(value >= 0.0 && value <= 1.0) {
				Global.MUTATION_RATE = value;
				Global.MUTATION_SIZE = (int) Math.round(Global.MUTATION_RATE * Global.MAX_SIZE_POPULATION);
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Mutation rate must be included between 0 and 1 : " + value);
				System.exit(1);
			}
		}
		
		// mutation threshold
		if(cmd.getOptionValue(Parameters.MUTATION_THRESHOLD) != null) {
			Double value = Double.parseDouble(cmd.getOptionValue(Parameters.MUTATION_THRESHOLD));
			// test if the mutation threshold is between 0 and 1
			if(value >= 0.0 && value <= 1.0) {
				Global.MUTATION_THR = value;
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : Mutation threshold must be included between 0 and 1 : " + value);
				System.exit(1);
			}
		}
		
		// Init output folder
		createOutputDirectory(Global.OUTPUT_PATH, file);
		
		// Write all printed text in log file
		logFile = new Logger(new FileWriter(Global.outputDir + "/log.txt"));
		
		// print the banner
		logFile.printBanner("SWRLRulesFromRDF", file.getName(), Global.SPARQL_ENDPOINT, Global.NB_EXECUTIONS, Global.MAX_SIZE_GENERATION, Global.MAX_SIZE_POPULATION, Global.CROSSOVER_RATE, Global.MUTATION_RATE, Global.MUTATION_THR, true);
		// file loaded 
		logFile.log("INFO", className, "file " + file.getPath() + " loaded ...");
		// folder created
		logFile.log("INFO", className, "folder " + Global.outputDir + " created ...");
		
		//Populate the data structure
		SWRLRulesRDF rdf = new SWRLRulesRDF(file.getPath());
		
		int i = 1;

		while (i <= Global.NB_EXECUTIONS)        
		{
			logFile.log("INFO", className, "BEGIN THE SAMPLE " + i + " ...");
			String fileNameGenerated = Global.outputName + "_" + String.valueOf(i) + ".txt";
			String pathGeneratedFile = Global.outputDir + "/" + fileNameGenerated;
			
			if (!rdf.run(pathGeneratedFile)) 
			{
				logFile.log("INFO", className, "END SAMPLE " + i + " ...");
				logFile.log("INFO", className, "BEGIN CHECK " + fileNameGenerated + " ...");
				
				int survived=0;
				
				// Create a new file which contains all consistents rules
				String pathConsistentRulesFile = Global.outputDir + "/" + Global.outputName + "_" + i + "_ConsistentRules.txt";
				FileWriter fileWriter = new FileWriter(pathConsistentRulesFile, true);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				try
				{
					FileReader inputFile = new FileReader(pathGeneratedFile);
					BufferedReader brInputFile = new BufferedReader(inputFile);
					String line = "";
					int nbLines = 0;
					logFile.log("INFO", className, "-------------------");
					while ((line = brInputFile.readLine()) != null)
					{
						String strPattern = line.substring(line.indexOf(".") + 1, line.lastIndexOf("&") + 1).trim();
						logFile.log("INFO", className, "Rule n°" + line.substring(0, line.indexOf(".")) + "> " + strPattern);
						try {
							survived += createRuleByText(pathConsistentRulesFile, strPattern);
						} catch (Exception e) {
							logFile.log("ERROR", className, "SPARQL Request cannot perform: " + e.getCause());
						}
						nbLines++;
					}
					double rate = ((double) survived / nbLines) * 100;
					logFile.log("INFO", className, "Number of rules survived: " + survived + " (" + Math.round(rate) + "% of the rules on " + fileNameGenerated + ")");
					logFile.log("INFO", className, "END CHECK " + fileNameGenerated);

					brInputFile.close();  
					writer.close();
				}
				catch (IOException e)
				{
					logFile.log("ERROR", className, e.getMessage());
				}  
				i++;
			}            

		}

		logFile.log("INFO", className, "END SWRLRulesFromRDF.");
		logFile.closeFile();
	}


	/**
	 * Read the rules from the file generated with the Genetic Algorithm and 
	 * for each generated rule went to query the dataset with a new query to understand if the 
	 * rule is true.
	 * If the number of elements returned by the query is greater than 3, the rule is considered false.
	 * Otherwise, the rule is considered true.
	 * @param path the path of file generated with the Genetic Algorithm
	 * @param strRule is the rule to check
	 * @return zero if there rule is not correct and one in the other case.
	 */
	public static  int createRuleByText(String path, String strRule) {
		try {
			FileWriter fileWriter = new FileWriter(path, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			//HEAD: create the string for the IRI, and the variables.
			String strHead = strRule.substring(0, strRule.indexOf("<=")).trim();
			String head, strVariable1, strVariable2 = null;
			
			if (strHead.contains(", ")) {
				head = strHead.substring(0, strHead.indexOf("(")).trim();
				strVariable1 = strHead.substring(strHead.indexOf("(") + 1, strHead.indexOf(",")).trim();
				strVariable2 = strHead.substring(strHead.indexOf(",") + 1, strHead.indexOf(")")).trim();
			} else {
				head = strHead.substring(0, strHead.indexOf("(")).trim();
				strVariable1 = strHead.substring(strHead.indexOf("(") + 1, strHead.indexOf(")")).trim();   
			}
	
			//BODY: create an arraylist containing all the IRI of the body and one containing for each position
			//the variables of the corresponding IRI
			String strBody = strRule.substring(strRule.indexOf("<=") + 2).trim();
			String[] body = strBody.split("&");
			ArrayList<String> variables = new ArrayList<String>();
			ArrayList<String> patternsBody = new ArrayList<String>();

			for(int i=0; i<body.length; i++) {
				String strName = body[i].substring(0, body[i].indexOf("(")).trim();
				patternsBody.add(strName);
				String var = body[i].substring(body[i].indexOf("(") + 1, body[i].indexOf(")")).trim();
				variables.add(var);
			}
	
			// SELECT: only the variables in the head
			String select = "SELECT DISTINCT ";
			if(strVariable2!=null) {
				select =select.concat(strVariable1).concat(" ");
				select =select.concat(strVariable2).concat(" ");
			} else
				select =select.concat(strVariable1).concat(" ");
	
			// WHERE: set conditions of query
			String where = "WHERE { "; 
			for(int i=0; i<patternsBody.size(); i++) {
				// Logger.log("INFO", logTag, "Variable " + (i + 1) + ": " + variables.get(i));
				if(variables.get(i).length()!=3 && variables.get(i).length()!=2) {
					where = where.concat((variables.get(i).substring(0, variables.get(i).indexOf(",")))).concat(" ");
					where = where.concat("<").concat(patternsBody.get(i)).concat("> ");
					where = where.concat((variables.get(i).substring(variables.get(i).indexOf(",")+1))).concat(". ");
				}
				else {
					where = where.concat(variables.get(i)).concat(" a ");
					where = where.concat("<").concat(patternsBody.get(i)).concat("> .");
				}
			}
	
			//FILTER
			//FILTER (NOT EXISTS {?x  <http://purl.org/spar/fabio/hasPubMedId> ?y} )}
			String filter = " FILTER (NOT EXISTS {";
			if(strVariable2!=null) {
				filter = filter.concat(strVariable1).concat(" ");
				filter = filter.concat("<").concat(head).concat("> " );
				filter = filter.concat(strVariable2).concat("})}");
			} else {
				filter = filter.concat(strVariable1).concat(" a ").concat("<").concat(head).concat("> })}");
			}
			
			String queryPattern = select.concat(where).concat(filter);
			logFile.log("INFO", className, "Query: " + queryPattern);
	
			Query query = QueryFactory.create(queryPattern);
			QueryExecution qexec = QueryExecutionFactory.sparqlService(Global.SPARQL_ENDPOINT, query);
	
			try {
				ResultSet results = qexec.execSelect();
				ResultSetFormatter.consume(results);
				if(results.getRowNumber() < 3) {
					writer.append(strRule);
					writer.append("\n");
					qexec.close();
					writer.close();
					logFile.log("INFO", className, "Is consistent ? YES");
					logFile.log("INFO", className, "-------------------");
					return 1;
				} else {
					qexec.close();
					writer.close();
					logFile.log("INFO", className, "Is consistent ? NO");
					logFile.log("INFO", className, "-------------------");
					return 0;
				}	
			} catch(HttpException e) {
				logFile.log("ERROR", className, "SPARQL request error: " + e.toString());
				return 0;
			}
			
		} catch(IOException e) {
			logFile.log("ERROR", className, "Cannot load file " + path + ": " + e.toString());
			return 0;
		}
		
	}
	
	/**
	 * Init output folder: output folder takes the name of current file without extension and current date.
	 * For instance: ./output/covid19/01012021_080000/ if the name of file is "covid19" processed on 1 January at 8 a.m
	 * @param outputPath the path of output directory
	 * @param file the instance of file to create an output folder with its name
	 */
	public static void createOutputDirectory(String outputPath, File file) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmss");  
	    Date date = new Date();
		Global.outputName = FilenameUtils.removeExtension(file.getName());
		Global.outputDir = outputPath + Global.outputName + "/" + format.format(date);
		File outputDirectory = new File(Global.outputDir);
		if(!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}
	}
	
}