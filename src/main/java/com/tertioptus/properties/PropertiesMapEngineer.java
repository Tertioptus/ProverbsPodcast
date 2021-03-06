package com.tertioptus.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.tertioptus.MapEngineer;
import com.tertioptus.ResourceStreamEngineer;

/**
 * Properties file mapping technician of files via a resource stream.
 *
 * @author Benjamin F. Paige III
 * @since Jan 19, 2019
 */
public final class PropertiesMapEngineer implements MapEngineer<String, String>{
	
	private String propertiesFileName;
	private Properties properties;
	private ResourceStreamEngineer<Properties> resourceStreamEngineer;

	public PropertiesMapEngineer(String fileName, ResourceStreamEngineer<Properties> resourceStreamEngineer) {
		this.propertiesFileName = fileName;
		this.properties = new Properties();
		this.resourceStreamEngineer = resourceStreamEngineer;
	}

	@Override
	public String value(String key) throws Exception {
		resourceStreamEngineer.load(propertiesFileName, properties);
		return properties.getProperty(key);
	}

	@Override
	public Map<String, String> generateMap() throws Exception {
		resourceStreamEngineer.load(propertiesFileName, properties);
		Map<String, String> map = new HashMap<>();
		this.properties.forEach((key,value) -> map.put((String)key, (String)value));
		return map;
	}
}
