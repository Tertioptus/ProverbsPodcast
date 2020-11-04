package com.tertioptus.rss.director;

import com.rometools.rome.feed.rss.Enclosure;

/**
 * An enclosure engineer is responsible for constructing a ROME enclosure object
 * from a URL
 * 
 * @author Tert
 *
 */
public interface EnclosureEngineer {

	Enclosure enclosure(String url) throws Exception;

}
