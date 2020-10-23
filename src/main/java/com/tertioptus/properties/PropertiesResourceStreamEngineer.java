package com.tertioptus.properties;

import java.io.InputStream;
import java.util.Properties;

import com.tertioptus.ResourceStreamEngineer;

/**
 * Java resource technician for loading properties utilities.
 *
 * @author Benjamin F. Paige III
 * @since Jan 19, 2019
 */
public final class PropertiesResourceStreamEngineer implements ResourceStreamEngineer<Properties> {

	private InputStream inpStream;
	
	/**
	 * Loads properties. 
	 */
	public void load(String resourceName, Properties properties) throws Exception {
		try {
			inpStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
			properties.load(inpStream);
		} finally {
			inpStream.close();
		}
	};
}
