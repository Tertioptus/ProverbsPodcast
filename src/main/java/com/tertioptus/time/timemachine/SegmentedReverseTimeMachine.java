package com.tertioptus.time.timemachine;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tertioptus.time.TimeMachine;

/**
 * {@link SegmentedReverseTimeMachine} produces time records based a on a
 * specified segment length, and a specified hour from which from to start.
 * 
 * @author Tert
 *
 */
public class SegmentedReverseTimeMachine implements TimeMachine {

	/**
	 * pruf.getLastBlockOfDayRecordFromTime: TimeMachine timeMachine = new
	 * SegmentedReverseTimeMachine(); byte[] blockOfDay =
	 * timeMachine.getLastBlockOfDayRecord(new SimpleDateFormat("yyyyMMdd
	 * HH:mm").parse("20201105 16:36")); assertTrue(blockOfDay[3] == 1);
	 */
	public byte[] getCurrent(LocalDateTime time, int segmentStartHour, int segmentLength) {
		/*
		 * The complexity here is derived from the non-linear sequencing of calendar
		 * dates. Therefore the block (of day) of the specified time must be identified
		 * first for the case where the block offset would span over a change in
		 * day/month/year (e.g. 2020-01-01 00:02 -> 2019-12-31 22:00). Then a block
		 * offset can be evaluated in order to leverage LocalDateTime's time change
		 * functionality to accurately remove the offset from the current time.
		 */
		int block = (time.getHour() - segmentStartHour) / segmentLength;
		int block_offset = time.getHour() - block;
		LocalDateTime currentBlockOfDayTime = time.minusHours(block_offset);
		return getBlockOfDay(block, currentBlockOfDayTime);
	}

	private byte[] getBlockOfDay(int block, LocalDateTime currentBlockOfDayTime) {
		return new byte[] { (byte) (currentBlockOfDayTime.getYear() % 100),
				(byte) currentBlockOfDayTime.getMonthValue(), (byte) currentBlockOfDayTime.getDayOfMonth(),
				(byte) block };
	}

	@Override
	public byte[] getPrevious(byte[] blockOfDay, int segmentStartHour, int segmentLength) {
		/*
		 * Cycling backwards through a sequence would normally be simple, but like the
		 * getCurrent method, we have to deal with the non-linear nature of calendar
		 * time. So here we must first convert a "block of day" record in to a full
		 * feature date entity, subtract a segment length, and then create a new
		 * "block of day" record out of the updated time.
		 */
		int currentBlock = blockOfDay[BlockOfDay.BLOCK.ordinal()];
		LocalDate date = LocalDate.parse(String.format("20%s-%2s-%2s", blockOfDay[BlockOfDay.YEAR.ordinal()],
				blockOfDay[BlockOfDay.MONTH.ordinal()], blockOfDay[BlockOfDay.DAY.ordinal()]).replace(' ', '0'));
		LocalDateTime currentTime = date.atTime(currentBlock * segmentLength + segmentStartHour, 0);
		int segmentsADay = (24 / segmentLength);
		int lastBlock = (segmentsADay + currentBlock - 1) % segmentsADay;
		return getBlockOfDay(lastBlock, currentTime.minusHours(segmentLength));
	}
}