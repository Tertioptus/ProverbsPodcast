package com.tertioptus.rss;

import java.io.File;
import java.util.List;

/**
 * Delivers an RSS artifact to specified locaiton when given a list of verses
 * 
 * @author BP3
 */
public interface Director {

	/**
	 * 
	 * @param target destination RSS artifact
	 * @param verses ordered list of verse arrays <div>array format:</div>
	 *               <ol>
	 *               <li>2 digit year</li>
	 *               <li>month</li>
	 *               <li>day</li>
	 *               <li>hour</li>
	 *               <li>verse</li>
	 *               </ol>
	 * @throws Exception implementation is like to have further IO operations
	 */
	void action(File target, List<byte[]> verses) throws Exception;

}
