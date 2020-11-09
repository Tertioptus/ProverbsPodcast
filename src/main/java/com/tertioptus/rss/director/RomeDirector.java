package com.tertioptus.rss.director;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.itunes.EntryInformationImpl;
import com.rometools.modules.itunes.FeedInformation;
import com.rometools.modules.itunes.FeedInformationImpl;
import com.rometools.modules.itunes.types.Category;
import com.rometools.modules.itunes.types.Duration;
import com.rometools.modules.itunes.types.Subcategory;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.WireFeedOutput;
import com.tertioptus.MapEngineer;
import com.tertioptus.rss.Director;

public class RomeDirector implements Director {

	private final MapEngineer<String, String> pe;
	private final ProverbsTechnician tech;
	private final EnclosureEngineer enclosureEngineer;
	private Double bitRate;

	public RomeDirector(MapEngineer<String, String> thepropertiesmapengineer, ProverbsTechnician tech,
			EnclosureEngineer enclosureEngineer) {
		this.pe = thepropertiesmapengineer;
		this.tech = tech;
		this.enclosureEngineer = enclosureEngineer;
	}

	/**
	 * proveThatTheRomeDirectorCanCreateAnRSSFile: 
	 * final File tempTargetFile =
	 * tempFolder.newFile("ProverbsPodcast.rss"); ProverbsTechnician
	 * proverbsTechnician; EnclosureEngineer enclosureEngineer; List<byte[]> verses
	 * = new ArrayList<>(); verses.add(new byte[] {20,10,8,9,1}); verses.add(new
	 * byte[] {20,11,26,12,8}); Director spike = new
	 * RomeDirector(thePropertiesMapEngineer,proverbsTechnician,enclosureEngineer);
	 * 
	 * 
	 * spike.action(tempTargetFile, verses);
	 * 
	 * // Verify generated content final String content =
	 * FileUtils.readFileToString(tempTargetFile);
	 * Assert.assertTrue(content.contains("Proverbs"));
	 */
	public void action(File target, List<byte[]> verses) throws Exception {
		Writer writer = new FileWriter(target);
		WireFeedOutput outputter = new WireFeedOutput();
		List<Item> items = new ArrayList<>();
		for (byte[] verse : verses) {
			items.add(item(verse[0], verse[1], verse[2], verse[3], verse[4]));
		}
		outputter.output(loadChannel(items), writer);
		writer.close();
	}

	private Item item(byte year, byte month, byte day, byte hour, byte verse) throws Exception {
		Item item = new Item();
		item.setTitle(String.format("Proverbs %s:%s", day, verse));

		Description description = new Description();
		description.setType("text");
		String proverb = tech.fetchProverb(day, verse);
		description.setValue(proverb);
		item.setDescription(description);
		// Enclosure represents a media file via link with file attributes
		// in the apple RSS spec
		Enclosure enclosure = enclosureEngineer
				.enclosure(String.format(pe.value("proverbs.root"), day, verse).replace(' ', '0'));
		item.setEnclosures(Arrays.asList(new Enclosure[] { enclosure }));
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
		item.setPubDate(dateFormat.parse(String.format("20%s-%2s-%2s,%2s:00:00", year, month, day, hour).replace(' ', '0')));
		EntryInformation entryInfo = new EntryInformationImpl();
		entryInfo.setKeywords(pe.value("keywords").split(","));
		entryInfo.setAuthor(pe.value("author"));
		entryInfo.setSummary(pe.value("summary"));
		entryInfo.setSubtitle(proverb);
		entryInfo.setDuration(duration(enclosure));
		item.getModules().add(entryInfo);
		return item;
	}

	private Duration duration(Enclosure enclosure) throws Exception {
		// The duration is inexplicably separate from the enclosure
		Duration duration = new Duration();
		if (bitRate == null) {
			bitRate = Double.parseDouble(pe.value("bitRate"));
		}
		duration.setMilliseconds((long) (enclosure.getLength() * bitRate));
		return duration;
	}

	private Channel loadChannel(List<Item> items) throws Exception {
		Channel channel = new Channel(pe.value("rss.spec"));
		channel.setLanguage(pe.value("language"));
		channel.setTitle(pe.value("title"));
		channel.setDescription(pe.value("description"));
		channel.setImage(getImage());
		channel.setLink(pe.value("host"));
		channel.setItems(items);
		FeedInformation feedInfo = new FeedInformationImpl();
		channel.getModules().add(feedInfo);
		feedInfo.setKeywords(pe.value("keywords").split(","));
		feedInfo.setOwnerEmailAddress(pe.value("owner.email"));
		feedInfo.setOwnerName(pe.value("owner.name"));
		Category category = new Category(pe.value("category"));
		category.setSubcategory(new Subcategory(pe.value("sub.category")));
		feedInfo.getCategories().add(category);
		feedInfo.setAuthor(pe.value("author"));
		feedInfo.setSubtitle(pe.value("sub.title"));
		feedInfo.setSummary(pe.value("summary"));
		feedInfo.setExplicit(false);
		return channel;
	}

	private Image getImage() throws Exception {
		Image image = new Image();
		image.setUrl(pe.value("image"));
		image.setTitle(pe.value("title"));
		image.setDescription(pe.value("description"));
		image.setHeight(350);
		image.setWidth(350);
		return image;
	}
}
