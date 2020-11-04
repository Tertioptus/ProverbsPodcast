package com.tertioptus.rss.director;

/**
 * Like a "rule enforcement" agent, this object serves as a blocking behavior
 * for illegal arguments.
 * 
 * @author P1836
 *
 */
public class ArgumentControlProverbsTechnician implements ProverbsTechnician {

	private final ProverbsTechnician dependent;

	public ArgumentControlProverbsTechnician(ProverbsTechnician proverbsTechnician) {
		this.dependent = proverbsTechnician;
	}

	public String fetchProverb(byte chapter, byte verse) throws Exception {
		validateChapter(chapter);
		if (verse < 1) {
			throw new Exception("The specified verse number must be greater than 1.");
		}
		return this.dependent.fetchProverb(chapter, verse);
	}

	public byte verseCount(byte chapter) throws Exception {
		validateChapter(chapter);
		return this.dependent.verseCount(chapter);
	}

	private void validateChapter(byte chapter) throws Exception {
		if (chapter < 1 || chapter > 31) {
			throw new Exception("The specified chapter must be between 1 and 31.");
		}
	}

}
