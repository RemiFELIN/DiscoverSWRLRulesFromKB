package com.i3s.app.consistency;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.i3s.app.common.Global;
import com.i3s.app.consistency.output.OutputInformation;
import com.i3s.app.consistency.pattern.CheckPattern;
import com.i3s.tools.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * It is used to verify that the 
 * rules obtained by the SWRLRulesFromOntology algorithm 
 * are consistent with the ontology.
 */
public class CheckConsistency 
{
	
	static String className = CheckConsistency.class.getName();
	
    public static void run(String path, Logger logFile)
    {        

    	File file = new File(path);
	    // Load file and url
		if(file.exists()) {
			// the file with the rules to check
			Global.INPUT_PATTERNS_IN_FILE = file.getAbsolutePath().replace("\\", "/");
		} else {
			logFile.log("ERROR", className, "File path is not correct ...");
			return;
		}
    	
		logFile.log("INFO", className, "STARTING " + file.getAbsolutePath() + " ANALYSIS ...");     
      
        // the file with the rules checked
        Global.OUTPUT_PATTERNS_IN_FILE = Global.outputDir + "/" + FilenameUtils.removeExtension(file.getName()) + "_ConsistentRules.txt";
        OutputInformation output = new OutputInformation(Global.OUTPUT_PATTERNS_IN_FILE);
        
        CheckPattern checkPattern = new CheckPattern(logFile);
        checkPattern.checkForListPatterns();
        
        output.closeFile();
        
        logFile.log("INFO", className, "END " + file.getAbsolutePath() + " ANALYSIS ...");
    }

}
