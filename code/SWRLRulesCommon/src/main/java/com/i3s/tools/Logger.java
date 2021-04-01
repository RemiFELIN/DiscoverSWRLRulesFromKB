package com.i3s.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Logger {
	
	public String className = Logger.class.getName();
	public FileWriter file;
	
	public Logger(FileWriter file) {
		this.file = file;
	}
	
	public FileWriter getFile() {
		return file;
	}

	public void setFile(FileWriter file) {
		this.file = file;
	}
	
	public void closeFile() {
		// close file
		try {
			file.close();
		} catch (IOException e) {
			System.out.println(getDate() + " [ERROR][" + className + "] " + e.toString());
		}
	}

	/**
	 * return the current date in String format
	 * @return the current date
	 */
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    return formatter.format(date);
	}
	
	/**
	 * Log a specific message on a specific module with a specific type
	 * @param type ERROR, WARN, INFO, ...
	 * @param c The name of current class with package (for instance: "com.i3s.app.Test")
	 * @param message your message
	 */
	public void log(String type, String c, String message) {
		writeLineOnFileAndPrint(getDate() + "[" + c + StringUtils.repeat(" ", 50 - c.length()) + "] " + type.toUpperCase() + " : " + message);
	}

	/**
	 * Print a banner (used when you execute a tools on this project) which contains the date of execution 
	 * and parameters values
	 * @param title the title of module.
	 * @param filepath path of file given.
	 * @param url It can be the sparql endpoint to make our requests (ex: http://covidontheweb.inria.fr/sparql) or URL of the knowledge base to be analyze (ex: ).
	 * @param nbExecutions the number of executions. (by default: 2)
	 * @param nbGenerations the number of generations. (by default: 200)
	 * @param crossoverSize the number of times to crossover between the patterns of a population. (by default: 500)
	 * @param mutationSize the number of times to mutation between the patterns of a population. (by default: 500)
	 * @param mutationThr the threshold thanks to which the specialization or the generalization is made. (by default: 0.2)
	 * @param toCheck a boolean to launch consistency check phase if true, else this tool is not used
	 */
	public void printBanner(String title, String filepath, String url, int nbExecutions, int nbGenerations, int crossoverSize, int mutationSize, double mutationThr, boolean toCheck) {
		writeLineOnFileAndPrint("************************************************************************************************************************************************");
		writeLineOnFileAndPrint("* " + title);
		writeLineOnFileAndPrint("* Date: " + getDate());
		writeLineOnFileAndPrint("* Parameters: ");
		writeLineOnFileAndPrint("* - File                  : " + filepath);
		writeLineOnFileAndPrint("* - URL                   : " + url);
		writeLineOnFileAndPrint("* - Number of executions  (default value: 2)   : " + nbExecutions);
		writeLineOnFileAndPrint("* - Number of generations (default value: 200) : " + nbGenerations);
		writeLineOnFileAndPrint("* - Crossover size        (default value: 500) : " + crossoverSize);
		writeLineOnFileAndPrint("* - Mutation size         (default value: 500) : " + mutationSize);
		writeLineOnFileAndPrint("* - Mutation threshold    (default value: 0.2) : " + mutationThr);
		writeLineOnFileAndPrint("* - Consistency check phase                    : " + (toCheck ? "YES" : "NO"));
		writeLineOnFileAndPrint("************************************************************************************************************************************************");
	}
	
	/**
	 * this method allow to write a file and print in console in same time
	 * @param line line to write on the log file
	 */
	public void writeLineOnFileAndPrint(String line) {
		System.out.println(line);
		// Write all printed text in file
		try {
			file.write(line + "\n");
		} catch (IOException e) {
			System.out.println(getDate() + " [ERROR][" + className + "] " + e.toString());
		}
	}
	
	/**
	 * this method allow to write a file
	 * @param line
	 */
	public void writeLineOnFile(String line) {
		// Write all printed text in file
		try {
			file.write(line + "\n");
		} catch (IOException e) {
			System.out.println(getDate() + " [ERROR][" + className + "] " + e.toString());
		}
	}

}
