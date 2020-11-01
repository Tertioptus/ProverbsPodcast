package com.tertioptus.rss.producer;

import java.io.File;
import java.util.Collections;

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

		director.action(new File ("Podcast.rss"), Collections.emptyList());
	}

}
