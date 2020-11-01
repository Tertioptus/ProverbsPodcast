package com.tertioptus.rss.director;

import java.net.URL;
import java.net.URLConnection;

import com.rometools.rome.feed.rss.Enclosure;

final class AnEnclosureEngineer implements EnclosureEngineer {

	@Override
	public Enclosure enclosure(String url) {
		Enclosure enclosure = new Enclosure();
		enclosure.setUrl(url);
		enclosure.setType("audio/mpeg");
		enclosure.setLength(length(url));
		return enclosure;
	}

	private long length(String url) {
		URLConnection conn;
		int size = 0;

		try {
			conn = new URL(url).openConnection();
			size = conn.getContentLength();
			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return size;
	}

}
