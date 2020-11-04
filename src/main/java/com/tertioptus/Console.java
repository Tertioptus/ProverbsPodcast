package com.tertioptus;

import com.tertioptus.properties.PropertiesMapEngineer;
import com.tertioptus.properties.PropertiesResourceStreamEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.Producer;
import com.tertioptus.rss.director.AnEnclosureEngineer;
import com.tertioptus.rss.director.AnOpenCSVProverbsTechnician;
import com.tertioptus.rss.director.ArgumentControlProverbsTechnician;
import com.tertioptus.rss.director.LookupCacheEngineer;
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

    	
    	producer(	thePropertiesMapEngineer,
    				new RomeDirector(thePropertiesMapEngineer,new ArgumentControlProverbsTechnician(new AnOpenCSVProverbsTechnician()), new SmartEnclosureEngineer(new AnEnclosureEngineer(), new LookupCacheEngineer())), 
    				new SegmentedReverseTimeMachine())
    	.start();
    }

	private static Producer producer(MapEngineer<String,String> mapEngineer, Director director, TimeMachine timeMachine) {
		return new AProducer(mapEngineer, director, timeMachine);
	}
}
