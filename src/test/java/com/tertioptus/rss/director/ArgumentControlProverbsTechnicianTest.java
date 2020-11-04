package com.tertioptus.rss.director;

import org.junit.Assert;
import org.junit.Test;

public class ArgumentControlProverbsTechnicianTest {

	private static final int PROVERBS_CHAPTER_VERSE_COUNT = 35;
	private static final int PROVERBS_CHAPTER = 35;

	@Test(expected = Exception.class)
	public void AProverbCanBeFetched() throws Exception {
		ProverbsTechnician tech = new ArgumentControlProverbsTechnician(new AnOpenCSVProverbsTechnician());
		String first = tech.fetchProverb((byte) 1, (byte) 0);
		Assert.assertTrue(first.contains("fear"));
	}
	
	@Test(expected = Exception.class)
	public void VersesInAChapterCanBeCounted() throws Exception {
		ProverbsTechnician tech = new ArgumentControlProverbsTechnician(new AnOpenCSVProverbsTechnician());
		byte verseCount = tech.verseCount((byte)PROVERBS_CHAPTER);
		Assert.assertEquals(verseCount, PROVERBS_CHAPTER_VERSE_COUNT);
	}
}