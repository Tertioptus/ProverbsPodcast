package com.tertioptus.rss.producer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tertioptus.MapEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.Producer;
import com.tertioptus.time.TimeMachine;

public class AProducer implements Producer {
	
	private final Director director;

	public AProducer(MapEngineer<String, String> mapEngineer, Director director, TimeMachine timeMachine) {
		this.director = director;
	}

	@Override
	public void start() throws Exception {
		List<byte[]> verses = new ArrayList<>();
		verses.add(new byte[] {20,10,8,9,1});
		verses.add(new byte[] {20,11,26,12,8});

		director.action(new File ("Podcast.rss"), verses);
	}

}
