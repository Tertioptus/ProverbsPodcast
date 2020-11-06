package com.tertioptus.time;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.tertioptus.time.timemachine.SegmentedReverseTimeMachine;

public class SegmentedReverseTimeMachineTest {
	
	private static final int SEGMENT_LENGTH = 8;
	private static final int SEGMENTS_START_HOUR = 4;

	@Test
	public void getCurrentBlockOfDayRecordFromTime() throws Exception {
		TimeMachine timeMachine = new SegmentedReverseTimeMachine();
		byte[] blockOfDay = timeMachine.getCurrent(LocalDateTime.parse("2020-11-05T16:36:00"),SEGMENTS_START_HOUR,SEGMENT_LENGTH);
		assertTrue(blockOfDay[TimeMachine.BlockOfDay.BLOCK.ordinal()] == 1);
	}
	
	@Test
	public void getPreviousBlockOfDayRecordFromABlockOfDayRecord() throws Exception {
		TimeMachine timeMachine = new SegmentedReverseTimeMachine();
		byte[] blockOfDay = timeMachine.getPrevious(new byte[] {20,10,15,0},SEGMENTS_START_HOUR,SEGMENT_LENGTH);
		assertTrue(blockOfDay[TimeMachine.BlockOfDay.BLOCK.ordinal()] == 2);
		assertTrue(blockOfDay[TimeMachine.BlockOfDay.DAY.ordinal()] == 14);
	}

}
