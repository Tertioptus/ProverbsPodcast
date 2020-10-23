package com.tertioptus.rss.director;

/**
 * Retrieves proverbs via csv file.
 * 
 * @author Ben
 *
 */
public interface ProverbsTechnician {
	static final String RESOURCE = "proverbs.csv";

	enum Columns {
		CHAPTER,
		VERSE,
		TEXT
	}
	
	String fetchProverb(byte chapter, byte verse) throws Exception;
}
