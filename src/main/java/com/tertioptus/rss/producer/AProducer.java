package com.tertioptus.rss.producer;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tertioptus.MapEngineer;
import com.tertioptus.rss.Director;
import com.tertioptus.rss.Producer;
import com.tertioptus.rss.director.ProverbsTechnician;
import com.tertioptus.time.TimeMachine;

public class AProducer implements Producer {

	private final Director director;
	private final MapEngineer<String, String> mapEngineer;
	private final TimeMachine timeMachine;
	private final ProverbsTechnician proverbsTechnician;

	public AProducer(MapEngineer<String, String> mapEngineer, Director director, TimeMachine timeMachine,
			ProverbsTechnician proverbsTechnician) {
		this.director = director;
		this.mapEngineer = mapEngineer;
		this.timeMachine = timeMachine;
		this.proverbsTechnician = proverbsTechnician;
	}

	@Override
	public void start() throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		List<byte[]> verses = new ArrayList<>();
		int segmentsStartHour = Integer.parseInt(mapEngineer.value("start.hour"));
		int segmentLength = Integer.parseInt(mapEngineer.value("segment.length"));
		int segmentsADay = 24 / segmentLength;
		byte[] currentBlock = timeMachine.getCurrent(currentTime, segmentsStartHour, segmentLength);
		do {
			verses.add(loadVerse(segmentsADay, segmentLength, segmentsStartHour, currentBlock));
			currentBlock = timeMachine.getPrevious(currentBlock, segmentsStartHour, segmentLength);
		} while(viable(currentBlock));
		director.action(new File(mapEngineer.value("rss.target_directory") + "/" + mapEngineer.value("filename")), verses);
	}

	private boolean viable(byte[] currentBlock) {
		if(	currentBlock[TimeMachine.BlockOfDay.YEAR.ordinal()] <= 19 &&
			currentBlock[TimeMachine.BlockOfDay.MONTH.ordinal()] <= 11 &&
			currentBlock[TimeMachine.BlockOfDay.DAY.ordinal()] <= 7 &&
			currentBlock[TimeMachine.BlockOfDay.BLOCK.ordinal()] <= 0
				) 
		{
			return false;
		}
		return true;
	}

	private byte[] loadVerse(int segmentsADay, int segmentLength, int segmentsStartHour, byte[] currentBlock) throws Exception {
		return new byte[] { currentBlock[TimeMachine.BlockOfDay.YEAR.ordinal()],
				currentBlock[TimeMachine.BlockOfDay.MONTH.ordinal()],
				currentBlock[TimeMachine.BlockOfDay.DAY.ordinal()],
				(byte)(currentBlock[TimeMachine.BlockOfDay.BLOCK.ordinal()] * segmentLength + segmentsStartHour),
				(byte) ((currentBlock[TimeMachine.BlockOfDay.BLOCK.ordinal()] + currentBlock[TimeMachine.BlockOfDay.MONTH.ordinal()] * segmentsADay)
						% this.proverbsTechnician.verseCount(currentBlock[TimeMachine.BlockOfDay.DAY.ordinal()])+1) };
	}

}
