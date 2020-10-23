package com.tertioptus.rss.director;

import com.rometools.rome.feed.rss.Enclosure;

public interface EnclosureEngineer {

	Enclosure enclosure(String url) throws Exception;

}
