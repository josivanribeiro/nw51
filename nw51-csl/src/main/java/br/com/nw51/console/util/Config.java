package br.com.nw51.console.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Config properties class.
 * 
 * @author Josivan Silva
 *
 */
public class Config {
	
	static Logger logger = Logger.getLogger (Config.class.getName());
	private static final String CONFIG_PROPERTIES_FILE = "app-config.properties";
	
	private Properties prop;

	public Config() {
		this.loadProperties ();
	}
	
	private void loadProperties () {
		this.prop   = new Properties();
		InputStream input = null;
		try {
			String configPath = Utils.getConfigPath();
			input = new FileInputStream (configPath + CONFIG_PROPERTIES_FILE);
			prop.load (input);			
		} catch (IOException e) {
			logger.error ("An IO error occurred while loading the properties file. " + e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error ("An IO error occurred while closing the input stream. " + e.getMessage());					
				}
			}
		}
	}
	
	/**
	 * Gets a property by its key.
	 * 
	 * @param key the property key.
	 * @return
	 */
	public String getProperty (String key) {
		return prop.getProperty (key);
	}
	
}
