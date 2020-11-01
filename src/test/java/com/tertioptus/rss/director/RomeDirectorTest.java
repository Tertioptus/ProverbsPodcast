package com.tertioptus.rss.director;

import java.io.File;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.tertioptus.properties.PropertiesMapEngineer;
import com.tertioptus.properties.PropertiesResourceStreamEngineer;
import com.tertioptus.rss.Director;

public class RomeDirectorTest {

	private final static PropertiesMapEngineer thePropertiesMapEngineer = new PropertiesMapEngineer("config.properties",
			new PropertiesResourceStreamEngineer());
		
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void proveThatTheRomeDirectorCanCreateAnRSSFile() throws Exception {
	
		// Generate test content
		final File tempTargetFile = tempFolder.newFile("ProverbsPodcast.rss");
		Director spike = new RomeDirector(thePropertiesMapEngineer);
		spike.action(tempTargetFile, Collections.emptyList());
		
		// Verify generated content
		final String content = FileUtils.readFileToString(tempTargetFile);
		Assert.assertTrue(content.contains("Proverbs 1:1"));
		//Assert.assertTrue(content.matches(".*Proverbs \\d:\\d.*"));
	}
}
