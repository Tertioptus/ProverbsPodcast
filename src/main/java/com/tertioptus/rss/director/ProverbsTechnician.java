package com.tertioptus.rss.director;

/**
 * Retrieves proverbs via CSV file.
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

	/**
	 * Returns the text content of the proverb. 
	 */
	String fetchProverb(byte chapter, byte verse) throws Exception;
	
	/**
	 * Returns the total count of verses within the specified chapter.
	 */
	byte verseCount(byte chapter) throws Exception;
}
