package com.tertioptus.rss.director;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tertioptus.rss.Director;

public class QualifyingDirector implements Director {
	
	private final Director director;
	
	public QualifyingDirector(Director director) {
		this.director = director;
	}

	@Override
	public void action(File target, List<byte[]> verses) throws Exception {
		List<byte[]> chapterAndVerses = new ArrayList<>();
		for(byte[] verse : verses) {
			if(notAlreadyAdded(verse[2],verse[4],chapterAndVerses)) {
				chapterAndVerses.add(verse);
			}
		}
		this.director.action(target, chapterAndVerses);
	}

	private boolean notAlreadyAdded(byte chapter, byte verse, List<byte[]> chapterAndVerses) {
		for(byte[] cAV : chapterAndVerses) {
			if(cAV[2] == chapter && cAV[4] == verse)
				return false;
		}
		return true;
	}

}
