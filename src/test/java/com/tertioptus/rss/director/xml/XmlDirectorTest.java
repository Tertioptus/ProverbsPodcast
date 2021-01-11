package com.tertioptus.rss.director.xml;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.rometools.rome.feed.rss.Enclosure;
import com.tertioptus.properties.PropertiesMapEngineer;
import com.tertioptus.properties.PropertiesResourceStreamEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.director.EnclosureEngineer;
import com.tertioptus.rss.director.ProverbsTechnician;

public class XmlDirectorTest {

	private final static PropertiesMapEngineer thePropertiesMapEngineer = new PropertiesMapEngineer("config.properties",
			new PropertiesResourceStreamEngineer());
		
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void proveThatTheRomeDirectorCanCreateAnRSSFile() throws Exception {
	
		// Generate test content
		final File tempTargetFile = tempFolder.newFile("ProverbsPodcast.rss");
		String myProverb = "Be patient.";
		ProverbsTechnician proverbsTechnician = mock(ProverbsTechnician.class);
		when(proverbsTechnician.fetchProverb((byte)8, (byte)1)).thenReturn(myProverb);
		when(proverbsTechnician.fetchProverb((byte)26, (byte)8)).thenReturn(myProverb);
		EnclosureEngineer enclosureEngineer = mock(EnclosureEngineer.class);
		Enclosure testEnclosure = new Enclosure();
		testEnclosure.setLength(1000);
		testEnclosure.setUrl("some url");
		when(enclosureEngineer.enclosure(anyString())).thenReturn(testEnclosure);
		List<byte[]> verses = new ArrayList<>();
		verses.add(new byte[] {20,10,8,9,1});
		verses.add(new byte[] {20,11,26,12,8});
		Director spike = new XmlDirector(thePropertiesMapEngineer,proverbsTechnician,enclosureEngineer,new ATextFileEngineer());
		spike.action(tempTargetFile, verses);
		
		// Verify generated content
		final String content = FileUtils.readFileToString(tempTargetFile);
		Assert.assertTrue(content.contains(myProverb));
	}
}
