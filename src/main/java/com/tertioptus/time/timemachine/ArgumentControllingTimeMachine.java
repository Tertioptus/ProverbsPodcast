package com.tertioptus.time.timemachine;

import java.time.LocalDateTime;

import com.tertioptus.time.TimeMachine;

/**
 * This {@link TimeMachine} complains if any arguments are out of bounds.
 * @author P1836
 *
 */
public class ArgumentControllingTimeMachine implements TimeMachine {
	
	private final TimeMachine timeMachine;
	
	public ArgumentControllingTimeMachine(TimeMachine timeMachine) {
		this.timeMachine = timeMachine;
	}

	@Override
	public byte[] getCurrent(LocalDateTime time, int segmentsStartHour, int segmentLength) throws Exception {
		applySegmentRules(segmentsStartHour, segmentLength);
		return this.timeMachine.getCurrent(time, segmentsStartHour, segmentLength);
	}

	private void applySegmentRules(int segmentsStartHour, int segmentLength) throws Exception {
		if(segmentsStartHour > 22) {
			throw new Exception("The start hour cannot be greater than 22.");
		}
		if(segmentLength > 23) {
			throw new Exception("The segment length cannot be greater than 23");
		}
		if(24 % segmentLength != 0) {
			throw new Exception("The segment length must be a factor of 24");
		}
	}

	@Override
	public byte[] getPrevious(byte[] blockOfDay, int segmentsStartHour, int segmentLength) throws Exception {
		applySegmentRules(segmentsStartHour, segmentLength);
		return this.timeMachine.getPrevious(blockOfDay, segmentsStartHour, segmentLength);
	}

}
