/**
 * 
 */
package fr.epita.quiz.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Class DataConnection.
 *
 * @author namrata
 */
public class DataConnection {
	
	/** The instance. */
	private static DataConnection instance;
	private static Connection conn;
	
	/**
	 * Instantiates a new data connection.
	 */
	private DataConnection() {}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public Connection getConnection() throws SQLException {
		
		if(conn == null)
		{
			Configuration conf = Configuration.getInstance();
			String jdbcUrl = conf.getConfigurationValue("jdbc.url");
			String user = conf.getConfigurationValue("jdbc.user");
			String password = conf.getConfigurationValue("jdbc.password");
			return DriverManager.getConnection(jdbcUrl, user, password);
		}
		return conn;
	}
	
	/**
	 * Gets the single instance of DataConnection.
	 *
	 * @return single instance of DataConnection
	 */
	public static DataConnection getInstance()
	{
		if (instance == null) {
			instance = new DataConnection();
		}
		return instance;
	
	}

}
