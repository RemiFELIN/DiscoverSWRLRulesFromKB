/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i3s.app.consistency.pattern;

import com.i3s.app.common.Global;
import com.i3s.tools.Logger;

/**
 *
 * @author tdminh
 */
public class ThreadCheckThread extends Thread
{
	
	static String className = ThreadCheckThread.class.getName();
	Logger logFile;
	private boolean exit = false; 

	public ThreadCheckThread(Logger logFile) {
		this.logFile = logFile;
	}
	
	public void termine() 
    { 
        exit = true; 
    } 
	
    public void run()
    {
    	
    	while (!exit) {
    		try {
    			Thread.sleep(Global.THREAD_SLEEP);
    		} catch (InterruptedException ex) {
    			logFile.log("ERROR", className, ex.toString());
    		}

    		if (Global.THREAD_SLEEP > 0)
    		{        
    			for(int i=0; i<Global.MAX_THREAD;i++)
    			{
    				if (Global.arrThreadCheckPattern[i].isAlive())
    				{
    					logFile.log("INFO", className, "Status thread " + (i+1) + " > Stopped");
    					Global.arrThreadCheckPattern[i].termine();
    					Global.iNumberOfError++;
    				}
    				else 
    				{
    					logFile.log("INFO", className, "Status thread " + (i+1) + " > Finished");
    					Global.arrThreadCheckPattern[i].termine();
    				}
    			}
    		}
    	}
    }
}
