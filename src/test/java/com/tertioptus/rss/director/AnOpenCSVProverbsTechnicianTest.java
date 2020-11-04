package com.tertioptus.rss.director;

import org.junit.Test;

import org.junit.Assert;

public class AnOpenCSVProverbsTechnicianTest {

	private static final int PROVERBS_CHAPTER_VERSE_COUNT = 35;
	private static final int PROVERBS_CHAPTER = 23;

	@Test
	public void AProverbCanBeFetched() throws Exception {
		ProverbsTechnician tech = new AnOpenCSVProverbsTechnician();
		String first = tech.fetchProverb((byte) 1, (byte) 7);
		Assert.assertTrue(first.contains("fear"));
	}
	
	@Test
	public void VersesInAChapterCanBeCounted() throws Exception {
		ProverbsTechnician tech = new AnOpenCSVProverbsTechnician();
		byte verseCount = tech.verseCount((byte)PROVERBS_CHAPTER);
		Assert.assertEquals(verseCount, PROVERBS_CHAPTER_VERSE_COUNT);
	}
}
