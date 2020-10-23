package com.tertioptus;

import com.tertioptus.rss.Director;
import com.tertioptus.rss.Producer;
import com.tertioptus.time.TimeMachine;

public abstract class Production {
	
	public void roll() throws Exception {
    	theProducer(
    			thePropertiesMapEngineer(),
				theDirector(thePropertiesMapEngineer()), 
				aTimeMachine()
			)
    	.start();	
	}
	
	protected abstract Director theDirector(MapEngineer<String,String> aPropertiesMapEngineer);
	protected abstract MapEngineer<String, String> thePropertiesMapEngineer();
	protected abstract Producer theProducer(MapEngineer<String, String> aPropertiesMapEngineer,Director aDirector, TimeMachine aTimeMachine);
	protected abstract TimeMachine aTimeMachine();
}
