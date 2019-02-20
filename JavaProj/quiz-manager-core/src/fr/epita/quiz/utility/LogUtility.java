package fr.epita.quiz.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * The Class LogUtility.
 */
public class LogUtility {


	
	/** The instance. */
	private static LogUtility instance;
	
	/** The log file. */
	private File logFile;
	private FileWriter fileWriter;

	/**
	 * Instantiates a new log utility.
	 */
	private LogUtility() {
		Configuration conf = Configuration.getInstance();
		String filePath = conf.getConfigurationValue("log.path");
		logFile = new File(filePath);
		try {
			fileWriter = new FileWriter(logFile, true);
			fileWriter.write(" Log file created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Log debug.
	 *
	 * @param className the class name
	 * @param message the message
	 */
	public void logDebug(String className, String message)
	{
		Date date = new Date();
		try {
			fileWriter.write(Constants.LOG_DEBUG + " "+ date + " " + className + " " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Log error.
	 *
	 * @param className the class name
	 * @param message the message
	 * @param e the exception
	 */
	public void logError(String className, String message, Exception e)
	{
		Date date = new Date();
		try {
			fileWriter.write(Constants.LOG_ERROR + " "+ date + " " + className + " " + message+ " " + e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Log error.
	 *
	 * @param className the class name
	 * @param message the message
	 */
	public void logError(String className, String message)
	{
		Date date = new Date();
		try {
			fileWriter.write(Constants.LOG_ERROR + " "+ date + " " + className + " " + message);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * Gets the single instance of LogUtility.
	 *
	 * @return single instance of LogUtility
	 */
	public static LogUtility getInstance()
	{
		if (instance == null) {
			instance = new LogUtility();
		}
		return instance;
	
	}
	
	public void closeLogs() 
	{
		try {
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
}
