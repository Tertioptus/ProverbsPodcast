package com.tertioptus.rss.director;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class AnOpenCSVProverbsTechnician implements ProverbsTechnician {

	private final Map<Integer, String> proverbsMap;
	private final Map<Byte,Byte> proverbsVerseCount;

	public AnOpenCSVProverbsTechnician() {
		this.proverbsMap = new HashMap<>();
		this.proverbsVerseCount = new HashMap<>();
	}

	public String fetchProverb(byte chapter, byte verse) throws Exception {
		Integer chapterAndVerse = generateVerseKey(chapter, verse);
		if (!proverbsMap.containsKey(chapterAndVerse)) {
			loadCache();
		}
		return proverbsMap.get(chapterAndVerse);
	}

	public byte verseCount(byte chapter) throws Exception {
		if(proverbsVerseCount.isEmpty()) {
			loadCache();
		}
		return proverbsVerseCount.get(chapter);
	}

	private void loadCache() throws Exception {
		CSVReader reader = loadCSVReader();
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			if (nextLine != null) {
				byte chapter = Byte.parseByte(nextLine[Columns.CHAPTER.ordinal()]);
				byte verse = Byte.parseByte(nextLine[Columns.VERSE.ordinal()]);
				byte verseCount = 0;
				proverbsMap.put(generateVerseKey(chapter, verse), 
						nextLine[Columns.TEXT.ordinal()]);
				
				if(!proverbsVerseCount.containsKey(chapter)) {
					proverbsVerseCount.put(chapter, verseCount);
				}
				verseCount = proverbsVerseCount.get(chapter);
				proverbsVerseCount.put(chapter, ++verseCount);
			}
		}
	}

	private CSVReader loadCSVReader() {
		/*
		 * "Resource as stream" must be used in order to load a file that's in the jar.
		 */
		return new CSVReader(
				new BufferedReader(
						new InputStreamReader(getClass().getResourceAsStream(RESOURCE))), '\t', '"', 1);
	}
	
	private Integer generateVerseKey(byte chapter, byte verse) {
		return chapter * 100 + verse;
	}
}
