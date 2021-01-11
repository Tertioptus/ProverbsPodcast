package com.tertioptus;

import java.util.Map;

/**
 * Technician of data structure maps.
 *
 * @author Benjamin F. Paige III
 * @since Jan 19, 2019
 * @param <T>
 * @param <K>
 */
public interface MapEngineer<T,K> {

	/**
	 * Returns the value registered at a specific key.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	K value(T key) throws Exception;
	
	Map<T,K> generateMap() throws Exception;
}
