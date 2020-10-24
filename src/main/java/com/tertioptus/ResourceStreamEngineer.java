package com.tertioptus;

/**
 * Resource stream technician.
 *
 * @author Benjamin F. Paige III
 * @since Feb 6, 2019
 * @param <T>
 */
public interface ResourceStreamEngineer<T> {

	/**
	 * Loads resource stream date into the given container.
	 */
	void load(String resourceName, T container) throws Exception;
}
