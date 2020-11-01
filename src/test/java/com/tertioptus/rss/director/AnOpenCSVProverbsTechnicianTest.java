package com.tertioptus.rss.director;

import org.junit.Test;

import org.junit.Assert;

public class AnOpenCSVProverbsTechnicianTest {

	@Test
	public void AProverbCanBeFetched() throws Exception {
		ProverbsTechnician tech = new AnOpenCSVProverbsTechnician();
		String first = tech.fetchProverb((byte) 1, (byte) 1);
		Assert.assertTrue(first.contains("fear"));
		
	}
}
