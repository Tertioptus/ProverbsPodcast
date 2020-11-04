package com.tertioptus.rss.director;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
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
		URL resource = this.getClass().getResource(RESOURCE);
		CSVReader reader = new CSVReader(new FileReader(new File(resource.toURI())), '\t', '"', 1);

		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			if (nextLine != null) {
				byte chapter = Byte.parseByte(nextLine[Columns.CHAPTER.ordinal()]);
				byte verse = Byte.parseByte(nextLine[Columns.VERSE.ordinal()]);
				proverbsMap.put(generateVerseKey(chapter, verse), 
						nextLine[Columns.TEXT.ordinal()]);
				
				byte verseCount = 0;
				if(!proverbsVerseCount.containsKey(chapter)) {
					proverbsVerseCount.put(chapter, verseCount);
				}
				verseCount = proverbsVerseCount.get(chapter);
				proverbsVerseCount.put(chapter, ++verseCount);
			}
		}
	}
	
	private Integer generateVerseKey(byte chapter, byte verse) {
		return chapter * 100 + verse;
	}
}
