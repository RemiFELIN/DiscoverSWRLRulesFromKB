package com.i3s.app;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.semanticweb.owlapi.model.IRI;

import com.i3s.app.common.Global;
import com.i3s.app.common.MyRandom;
import com.i3s.app.common.Parameters;
import com.i3s.app.consistency.CheckConsistency;
import com.i3s.app.dataprocessing.ConceptProcessing;
import com.i3s.app.dataprocessing.IndividualProcessing;
import com.i3s.app.dataprocessing.RoleProcessing;
import com.i3s.app.geneticalgorithm.GeneticAlgorithm;
import com.i3s.app.geneticalgorithm.Population;
import com.i3s.app.knowledge.KnowledgeBase;
import com.i3s.app.output.OutputInformation;
import com.i3s.tools.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minh and Valeria Bellusci
 */
public class SWRLRulesOntology 
{   
	
	public static Logger logFile;
	public static Logger resumeFile;
	
	public static String className = SWRLRulesOntology.class.getName();
	
	private KnowledgeBase kbStratified;
	public static boolean toCheck = false;
	
    /**
	 * This method is used to populate the data structure containing all the main elements of the data-sets.
	 * All these elements are taken from the owl file, using a reasoner.
	 * @param path the path of current file given
	 */
    public SWRLRulesOntology(String path)
    {   
        Global.myRandom = new MyRandom();
        this.kbStratified = new KnowledgeBase(Global.IRI_INPUT_STRATIFIED); 
        ConceptProcessing.createFrequentConceptsStratified(this.kbStratified, Global.FR_THR);
        ConceptProcessing.createConceptIsSubsumedByConcepts(kbStratified);
        ConceptProcessing.createConceptSubsumsConcepts(kbStratified);
        RoleProcessing.createFrequentRolesStratified(this.kbStratified, Global.FR_THR);
        RoleProcessing.createRoleIsSuperPropertyRoles(this.kbStratified);
        RoleProcessing.createRoleIsSubPropertyRoles(this.kbStratified);
        RoleProcessing.createConceptsDomainOfRole(this.kbStratified);
        RoleProcessing.createConceptsRangeOfRole(this.kbStratified);
        IndividualProcessing.getAllOfIndividuals(this.kbStratified);
    }
    
    /**
     * Initialize all parameters needed to execute this tool
     * @param args the arguments given at execution
     * @return cmd an instance of the CommandLine if all parameters are avalaible, else null
     */
    public static CommandLine initParameters(String args[]) {
    	
    	Options options = new Options();
		
    	Option paramFile = new Option(Parameters.FILE, true, Parameters.FILE_DESCRIPTION);
		Option paramUrl = new Option(Parameters.PREFIX_KB_URL, Parameters.PREFIX_KB_URL_FULL, true, Parameters.PREFIX_KB_URL_DESCRIPTION);
		Option paramNexe = new Option(Parameters.NUMBER_OF_EXECUTION, Parameters.NUMBER_OF_EXECUTION_FULL, true, Parameters.NUMBER_OF_EXECUTION_DESCRIPTION);
		Option paramNgen = new Option(Parameters.NUMBER_OF_GENERATION, Parameters.NUMBER_OF_GENERATION_FULL, true, Parameters.NUMBER_OF_GENERATION_DESCRIPTION);
		Option paramPosz = new Option(Parameters.POPULATION_SIZE, Parameters.POPULATION_SIZE_FULL, true, Parameters.POPULATION_SIZE_DESCRIPTION);
		Option paramCrrt = new Option(Parameters.CROSSOVER_RATE, Parameters.CROSSOVER_RATE_FULL, true, Parameters.CROSSOVER_RATE_DESCRIPTION);
		Option paramMtrt = new Option(Parameters.MUTATION_RATE, Parameters.MUTATION_RATE_FULL, true, Parameters.MUTATION_RATE_DESCRIPTION);
		Option paramMtth = new Option(Parameters.MUTATION_THRESHOLD, Parameters.MUTATION_THRESHOLD_FULL, true, Parameters.MUTATION_THRESHOLD_DESCRIPTION);
		Option paramCstf = new Option(Parameters.CHECK_CONSISTENCY_FILE, Parameters.CHECK_CONSISTENCY_FILE_FULL, true, Parameters.CHECK_CONSISTENCY_FILE_DESCRIPTION);
		
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
		paramCstf.setRequired(false);
		
		options.addOption(paramFile);
		options.addOption(paramUrl);
		options.addOption(paramNexe);
		options.addOption(paramNgen);
		options.addOption(paramPosz);
		options.addOption(paramCrrt);
		options.addOption(paramMtrt);
		options.addOption(paramMtth);
		options.addOption(paramCstf);
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args);
		} catch(ParseException e) {
			System.out.println(Logger.getDate() + " [" + SWRLRulesOntology.className + "] Parameters are not corrects");
			formatter.printHelp(className, options);
            System.exit(1);
		}
		
		return cmd;
    }
    
    /**
     * @param strFileName the file in which the rules will be written.
     * @param strFileResume the file in which the resume 
     * @return true if everything is okay.
     */
    public boolean run(String strFileName, String strFileResume)
    {
    	// First of all, we write a resume of genetical algorithm result
        OutputInformation output = new OutputInformation(strFileResume);
        
        double sumAverageFitness = 0;
        double dblAverageFitness = 0;
    
        double sumFitnessBiggerThan0 = 0;
        double dblAverageFitnessBiggerThan0 = 0;        
                
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        
        logFile.log("INFO", className, "Initialization of the initial population (creation of patterns using the atoms in the list of frequent concepts and frequent roles)");
        
        Population population = new Population();
        
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
                OutputInformation.showTextln("Generation:\t" + i + "\t" + population.getAverageFitness() + "\t" + 
                		population.getAverageFitnessBiggerThan0() + "\t" + population.getCountPatternFitnessBiggerThan0(), false);
                sumAverageFitness += population.getAverageFitness();
                sumFitnessBiggerThan0 += population.getAverageFitnessBiggerThan0();
            }
            
            catch(java.lang.OutOfMemoryError e)
            {
            	logFile.log("ERROR", className, e.getMessage());
                blRunAgain = true;
                break;
            }
        }
        
        if (!blRunAgain)
        {        
            dblAverageFitness = sumAverageFitness / Global.MAX_SIZE_GENERATION;
            dblAverageFitnessBiggerThan0 = sumFitnessBiggerThan0 / Global.MAX_SIZE_GENERATION;

            OutputInformation.showTextln("", false);
            OutputInformation.showTextln("Average of sum Average Fitness of Stratified Ontology: " + dblAverageFitness, false);
            OutputInformation.showTextln("Average of sum of Average Fitness > 0 of Stratified Ontology: " + dblAverageFitnessBiggerThan0, false);
            OutputInformation.showTextln("", false);
            
            output.closeFile();
            
            // Then, we write the rules on the file given by strFileName
            output = new OutputInformation(strFileName);
            
            logFile.log("INFO", className, "end genetic algorithm...");

            int count = 0;

            logFile.log("INFO", className, "Size of final population: " + population.getListIndividuals().size());
            logFile.log("INFO", className, "The number of times to crossover between the patterns of a population: " + Global.CROSSOVER_SIZE);
            logFile.log("INFO", className, "The number of times to mutation between the patterns of a population: " + Global.MUTATION_SIZE);
            
            int count2 = 0;
            for(int i = 0; i < population.getListIndividuals().size(); i++)
            {   
                if (population.getListIndividuals().get(i).getPatternComputation().getFitnessValue() > 0) {
                    ++count;
                    OutputInformation.showText(count + ". ", false);
                    OutputInformation.showPattern(population.getListIndividuals().get(i), false);
                } else {
                	count2++;
                }

            }
            logFile.log("INFO", className, "Size of final population: " + "Survived " + count + " patterns.");
            logFile.log("INFO", className, "Lost " + count2 + " patterns, because the fintess values were less than zero.");
        }
        
        output.closeFile();    
        
        return blRunAgain;
    }
    
    /**
	 * The entry point of the Genetic Algorithm application.
     * @param args 
     * @throws Exception 
	 */
    public static void main(String args[]) throws Exception
    {
    	// initialize our parameters using Apache commons API
    	CommandLine cmd = initParameters(args);
    	
		// FILE
	    File file = new File(cmd.getOptionValue(Parameters.FILE));
	    // check the path
		if(file.exists()) {
			Global.file_name_stratified = "file:" + file.getAbsolutePath().replace("\\", "/");
			Global.IRI_INPUT_STRATIFIED = IRI.create(Global.file_name_stratified);
		} else {
			System.out.println(Logger.getDate() + " [" + className + "] ERROR : File path is not correct: " + cmd.getOptionValue(Parameters.FILE));
			System.exit(1);
		}
		
		// URL
		Global.BASE_URL = cmd.getOptionValue(Parameters.PREFIX_KB_URL);
		
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
		
		// check consistency file 
		if(cmd.getOptionValue(Parameters.CHECK_CONSISTENCY_FILE) != null) {
			File fullOntologyFile = new File(cmd.getOptionValue(Parameters.CHECK_CONSISTENCY_FILE));
			if(fullOntologyFile.exists()) {
				setToCheck(true);
				Global.FILE_NAME_FULL = "file:///" + fullOntologyFile.getAbsolutePath().replace("\\", "/");
				Global.IRI_INPUT_FULL = IRI.create(Global.FILE_NAME_FULL);
			} else {
				System.out.println(Logger.getDate() + " [" + className + "] ERROR : File path is not correct: " + cmd.getOptionValue(Parameters.CHECK_CONSISTENCY_FILE));
				System.exit(1);
			}
		}
		
		// Init output folder
		createOutputDirectory(Global.OUTPUT_PATH, file);
		
		// Write all printed text in log file
    	logFile = new Logger(new FileWriter(Global.outputDir + "/log.txt"));
		
		// print the banner
    	logFile.printBanner("SWRLRulesOntology", file.getName(), Global.BASE_URL, Global.NB_EXECUTIONS, Global.MAX_SIZE_GENERATION,Global.MAX_SIZE_POPULATION, Global.CROSSOVER_RATE, Global.MUTATION_RATE, Global.MUTATION_THR, isToCheck());
		// file loaded 
    	logFile.log("INFO", className, "file " + file.getPath() + " loaded ...");
		// folder created
    	logFile.log("INFO", className, "folder " + Global.outputDir + " created ...");
		
	    //Populate the data structure
		SWRLRulesOntology ontology = new SWRLRulesOntology(file.getPath());
		int i = 1;
      
		while (i <= Global.NB_EXECUTIONS)        
		{
			logFile.log("INFO", className, "Begin the sample " + i + " ...");            
			String pathGeneratedFile = Global.outputDir + "/" + Global.outputName + "_" + String.valueOf(i) + ".txt";
			String pathResumeFile = Global.outputDir + "/" + Global.outputName + "_" + String.valueOf(i) + "_GA_Resume.txt";

			// if check phase is activated, we need to save all files to be checked
			if(isToCheck()) {
				Global.LIST_INPUT_PATTERNS_IN_FILE.add(pathGeneratedFile);
			}
			
			if (!ontology.run(pathGeneratedFile, pathResumeFile)) {
				logFile.log("INFO", className, "End the sample " + i + " ...");
				i++;
			}

		}
		logFile.log("INFO", className, "END SWRLRulesOntology.");
		
		// Consistency check phase
		if(isToCheck()) {
			logFile.log("INFO", className, "*************************************************************");
			logFile.log("INFO", className, "BEGIN Consistency check phase");
			for(String path : Global.LIST_INPUT_PATTERNS_IN_FILE) {
				CheckConsistency.run(path, logFile);
			}
			logFile.log("INFO", className, "END Consistency check phase");
		}
		
		logFile.closeFile();
    }


	public static boolean isToCheck() {
		return toCheck;
	}


	public static void setToCheck(boolean toCheck) {
		SWRLRulesOntology.toCheck = toCheck;
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
