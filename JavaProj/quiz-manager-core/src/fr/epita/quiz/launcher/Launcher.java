
package fr.epita.quiz.launcher;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.Server;

import fr.epita.quiz.utility.Configuration;
import fr.epita.quiz.utility.DataConnection;
import fr.epita.quiz.utility.LogUtility;

/**
 * The Class Launcher.
 *
 * @author namrata
 */
public class Launcher {
	
	/** The logger. */
	private static String className = "Launcher";
	
	/** The log utility. */
	private static LogUtility logUtility = LogUtility.getInstance();

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		try {
			Server server = Server.createTcpServer(args).start();
			logUtility.logDebug(className,"H2 Data Server started");
			
			if(server != null)
			{
				AdditionalServices addService = new AdditionalServices();
				addService.displayMainMenu();
				
				DataConnection dataConnection = DataConnection.getInstance();
				Connection connection = dataConnection.getConnection();
				connection.close();
				
				server.stop();
				logUtility.logDebug(className,"H2 Data Server stoped");
			}
			
		} catch (SQLException e) {
			
			logUtility.logDebug(className,"Exception occured : " + e);
		}
		logUtility.closeLogs();
		
	}
	
	

}
