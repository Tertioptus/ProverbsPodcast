package com.tertioptus.rss.director;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

	public RomeDirector(MapEngineer<String,String> thepropertiesmapengineer,
			ProverbsTechnician tech) {
		this.pe = thepropertiesmapengineer;
		this.tech = tech;
	}

	public void action(File target, List<byte[]> verses) throws Exception {
		Writer writer = new FileWriter(target);
		WireFeedOutput outputter = new WireFeedOutput();
		outputter.output(loadChannel(), writer);
		writer.close();
	}
	
	private Item item(byte year, byte month, byte day, byte hour, byte verse) throws Exception {
		Item item = new Item();
		item.setTitle(String.format("Proverbs {}:{}", day, verse));

		Description description = new Description();
		description.setType("text");
		description.setValue(tech.fetchProverb(day, verse));
		item.setDescription(description);
		// Enclosure represents a media file via link with file attributes
		// in the apple RSS spec
		Enclosure enclosure = enclosureEngineer.enclosure(url);
		item.setEnclosures(enclosure);
		item.setPubDate(new Date()); 
		EntryInformation entryInfo = new EntryInformationImpl();
		entryInfo.setKeywords(pe.value("keywords").split(","));
		entryInfo.setAuthor(pe.value("author"));
		entryInfo.setSummary(pe.value("summary"));
		entryInfo.setSubtitle(pe.value("sub.title"));
		//The duration is inexplicably separate from the enclosure
		Duration duration = new Duration();
		duration.setMilliseconds(enclosure.length()); //TODO
		entryInfo.setDuration(duration);
		item.getModules().add(entryInfo);
		return item;
	}
	
	private Channel loadChannel() throws Exception {
		Channel channel = new Channel(pe.value("rss.spec"));
		channel.setLanguage(pe.value("language"));
		channel.setTitle(pe.value("title"));
		channel.setDescription(pe.value("description"));
		channel.setImage(getImage());
		channel.setLink(pe.value("host"));
		channel.setItems(Arrays.asList(new Item[] {item()})); //TODO
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
		image.setHeight(1446);
		image.setWidth(1446);
		return image;
	}
}
