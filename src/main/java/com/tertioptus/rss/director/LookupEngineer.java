package com.tertioptus.rss.director;

public interface LookupEngineer {
	
	String value(String resource, String key) throws Exception;

	void record(String resource, String key, String value) throws Exception;
}
