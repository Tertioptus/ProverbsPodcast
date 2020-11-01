package com.tertioptus.rss.director;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class AnOpenCSVProverbsTechnician implements ProverbsTechnician {

	private final Map<Integer, String> proverbsMap;

	public AnOpenCSVProverbsTechnician() {
		this.proverbsMap = new HashMap<>();
	}

	public String fetchProverb(byte chapter, byte verse) throws Exception {
		Integer chapterAndVerse = generateVerseKey(chapter, verse);
		if (!proverbsMap.containsKey(chapterAndVerse)) {
			loadCache();
		}
		return proverbsMap.get(chapterAndVerse);
	}

	private void loadCache() throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(RESOURCE), '\t', '"', 1);

		// Read CSV line by line and use the string array as you want
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			if (nextLine != null) {
				String chapter = nextLine[Columns.CHAPTER.ordinal()];
				String verse = nextLine[Columns.VERSE.ordinal()];
				proverbsMap.put(generateVerseKey(chapter, verse), 
						nextLine[Columns.TEXT.ordinal()]);
			}
		}
	}
	
	private Integer generateVerseKey(String chapter, String verse) {
		return generateVerseKey(Integer.parseInt(chapter), Integer.parseInt(verse));
	}
	
	private Integer generateVerseKey(int chapter, int verse) {
		return chapter * 100 + verse;
	}

}
