package com.tertioptus.rss.director.xml;

public interface TextFileEngineer {
	
	@SuppressWarnings("rawtypes")
	String readAll(Class cls, String filename) throws Exception;
}
