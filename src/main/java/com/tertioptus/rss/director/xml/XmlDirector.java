package com.tertioptus.rss.director.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.text.StringSubstitutor;

import com.rometools.rome.feed.rss.Enclosure;
import com.tertioptus.MapEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.director.EnclosureEngineer;
import com.tertioptus.rss.director.ProverbsTechnician;

public class XmlDirector implements Director {

	private enum Phase {
		HEADER, EPISODES, FOOTER
	}
	
	private final MapEngineer<String,String> thePropertiesEngineer;
	private final ProverbsTechnician proverbsTechnician;
	private final EnclosureEngineer enclosureEngineer;
	private final TextFileEngineer textFileEngineer;

	public XmlDirector(MapEngineer<String,String> thePropertiesEngineer, ProverbsTechnician proverbsTechnician
			, EnclosureEngineer enclosureEngineer, TextFileEngineer textFileEngineer) {
		this.thePropertiesEngineer = thePropertiesEngineer;
		this.proverbsTechnician = proverbsTechnician;
		this.enclosureEngineer = enclosureEngineer;
		this.textFileEngineer = textFileEngineer;
	}
	
	@Override
	public void action(File target, List<byte[]> verses) throws Exception {
		/*
		 * The RSS feed template file is read line by line, appending to a header
		 * string, until the "items" portion of the file is reached. At this point, the
		 * header string is processed for value substitution; with the results being the
		 * written to the RSS output file. Episode items are then written to the file
		 * one by one. Finally, the process ends with writing the remaining lines in the
		 * template file to the output file.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(XmlDirector.class.getResourceAsStream("Feed-Template.xml")));
		Writer writer = new FileWriter(target);
		String line;
		Phase phase = Phase.HEADER;
		StringBuffer headerBuffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			switch (phase) {
			case HEADER:
				if (line.contains("<items/>")) {
					phase = Phase.EPISODES;
					continue;
				}
				headerBuffer.append(line + "\r\n");
				break;
			case EPISODES:
				writer.write(resolve(headerBuffer.toString()));
				writeItems(writer, verses);
				phase = Phase.FOOTER;
				break;
			case FOOTER:
				writer.write(line);
				break;
			}
		}
		writer.close();
		reader.close();
	}

	private String resolve(String templateData) throws Exception {
		return new StringSubstitutor(thePropertiesEngineer.generateMap()).replace(templateData);
	}

	private void writeItems(Writer writer, List<byte[]> verses) throws Exception {
		String itemTemplate = this.textFileEngineer.readAll(this.getClass(),"Item-Template.xml");
		for (byte[] verse : verses) {
			writer.write(item(itemTemplate, verse[0], verse[1], verse[2], verse[3], verse[4]) + "\r\n");
		}
	}

	private String item(String itemTemplate, byte year, byte month, byte day, byte hour, byte verse) throws Exception {
		Map<String, String> valueMap = new HashMap<>();
		String proverb = proverbsTechnician.fetchProverb(day, verse).trim().replace("\n", "").replace("\r", "");
		valueMap.put("description", proverb);
		valueMap.put("subtitle", proverb);
		valueMap.put("title", String.format("Proverbs %s:%s", day, verse));
		String url = String.format(thePropertiesEngineer.value("proverbs.root"), day, verse).replace(' ', '0');
		valueMap.put("url", url);
		Enclosure enclosure = this.enclosureEngineer.enclosure(url);
		valueMap.put("length", Long.toString(enclosure.getLength()));
		valueMap.put("author", thePropertiesEngineer.value("author"));
		valueMap.put("summary", thePropertiesEngineer.value("summary"));
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
		valueMap.put("pubDate",dateFormat.parse(String.format("20%s-%2s-%2s,%2s:00:00", year, month, day, hour).replace(' ', '0')).toString());
		valueMap.put("guid",  generateGuid(year,month,day, url));
		valueMap.put("keywords",  thePropertiesEngineer.value("keywords"));
		valueMap.put("duration",duration(enclosure));
		return new StringSubstitutor(valueMap).replace(itemTemplate);
	}

	private String duration(Enclosure enclosure) throws Exception {
		long millis = (long) (enclosure.getLength() * Double.parseDouble(thePropertiesEngineer.value("bitRate")));
		return String.format("%02d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(millis),
			    TimeUnit.MILLISECONDS.toMinutes(millis) - 
			    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}
	
	private String generateGuid(byte year, byte month, byte day, String url) throws UnsupportedEncodingException {
		return url + "?pubDate=" + String.format("20%s-%2s-%2s",year,month,day).replace(' ', '0');
	}
}
