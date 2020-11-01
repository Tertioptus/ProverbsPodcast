package com.tertioptus.rss;

import com.rometools.rome.feed.rss.Enclosure;

public class SmartEnclosureEngineer implements EnclosureEngineer {

	private static final String RESOURCE = "size-cache.csv";
	private final EnclosureEngineer enclosureEngineer;
	private final LookupEngineer lookupEngineer;

	public SmartEnclosureEngineer(EnclosureEngineer enclosureEngineer, LookupEngineer lookupEngineer) {
		this.enclosureEngineer = enclosureEngineer;
		this.lookupEngineer = lookupEngineer;
	}

	@Override
	public Enclosure enclosure(String url) throws Exception {
		String size = lookupEngineer.value(RESOURCE, url);
		Enclosure enclosure;
		if (size.isEmpty()) {
			enclosure = this.enclosureEngineer.enclosure(url);
			size = Long.toString(enclosure.getLength());
			lookupEngineer.record(RESOURCE, url, size);
		} else {
			enclosure = new Enclosure();
			enclosure.setUrl(url);
			enclosure.setType("audio/mpeg");
		}
		enclosure.setLength(Long.parseLong(size));
		return enclosure;
	}
}
