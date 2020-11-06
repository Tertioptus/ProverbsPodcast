package com.tertioptus.time;

import java.time.LocalDateTime;
import com.tertioptus.Production;

/**
 * This main {@link Production} prop produces a "block of day" record from a past "segment" of
 * time. A segment being a specified amount of time within 24 hours, such that
 * the beginning of the current or last segment, is the desirable next "block of day" record.
 *
 */
public interface TimeMachine {
	
	enum BlockOfDay {
		YEAR,
		MONTH,
		DAY,
		BLOCK
	}

	byte[] getCurrent(LocalDateTime time, int segmentsStartHour, int segmentLength) throws Exception;

	/**
	 * Because calendar dates do not have linear continuity the behavior to bridge
	 * those gaps is captured here.
	 */
	byte[] getPrevious(byte[] blockOfDay, int segmentsStartHour, int segmentLength) throws Exception;

}
