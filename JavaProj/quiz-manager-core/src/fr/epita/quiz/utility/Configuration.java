/**
 * 
 */
package fr.epita.quiz.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class Configuration.
 *
 * @author namrata
 */
public class Configuration {

	/** The properties. */
	private Properties properties;

	/** The instance. */
	private static Configuration instance;
	
	/**
	 * Instantiates a new configuration.
	 */
	private Configuration() {
		this.properties = new Properties();
		String confLocation = System.getProperty(Constants.CONFIG_LOCATION);
		try (InputStream is = new FileInputStream(new File(confLocation))) {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the single instance of Configuration.
	 *
	 * @return the instance
	 */
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	/**
	 * Gets the configuration value.
	 *
	 * @param configurationKey the configuration key
	 * @return the properties
	 */
	public String getConfigurationValue(String configurationKey) {
		return properties.getProperty(configurationKey);
	}

}
