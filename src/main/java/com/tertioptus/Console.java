package com.tertioptus;

import com.tertioptus.properties.PropertiesMapEngineer;
import com.tertioptus.properties.PropertiesResourceStreamEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.Producer;
import com.tertioptus.rss.director.AnEnclosureEngineer;
import com.tertioptus.rss.director.AnOpenCSVProverbsTechnician;
import com.tertioptus.rss.director.ArgumentControlProverbsTechnician;
import com.tertioptus.rss.director.LookupCacheEngineer;
import com.tertioptus.rss.director.ProverbsTechnician;
import com.tertioptus.rss.director.RomeDirector;
import com.tertioptus.rss.director.SmartEnclosureEngineer;
import com.tertioptus.rss.producer.AProducer;
import com.tertioptus.time.TimeMachine;
import com.tertioptus.time.timemachine.SegmentedReverseTimeMachine;

/**
 * Hello world!
 *
 */
public class Console 
{
	private final static PropertiesMapEngineer thePropertiesMapEngineer = new PropertiesMapEngineer("config.properties",
			new PropertiesResourceStreamEngineer());
	
    public static void main( String[] args ) throws Exception
    {

    	ProverbsTechnician proverbsTechnician = new ArgumentControlProverbsTechnician(new AnOpenCSVProverbsTechnician());
    	producer(	thePropertiesMapEngineer,
    				new RomeDirector(thePropertiesMapEngineer, proverbsTechnician, new SmartEnclosureEngineer(new AnEnclosureEngineer(), new LookupCacheEngineer())), 
    				new SegmentedReverseTimeMachine(),
    				proverbsTechnician)
    	.start();
    }

	private static Producer producer(MapEngineer<String,String> mapEngineer, Director director, TimeMachine timeMachine, ProverbsTechnician proverbsTechnician) {
		return new AProducer(mapEngineer, director, timeMachine, proverbsTechnician);
	}
}
