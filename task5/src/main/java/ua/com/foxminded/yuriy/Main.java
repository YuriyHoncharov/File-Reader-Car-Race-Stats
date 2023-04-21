
package ua.com.foxminded.yuriy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {
	public static void main(String[] args) {				
			
		DataHandle formulaUnoDataHandler = new DataHandle();
		FileHandle filesHandler = new FileHandle();
		
		List<String> racerDataList = filesHandler.getArrayList(FileHandle.racerDataFile);
		List<String> racerStartTimeList = filesHandler.getArrayList(FileHandle.startDataFile);
		List<String> racerEndTimeList = filesHandler.getArrayList(FileHandle.endDataFile);			
				
		LinkedHashMap<String, LocalDateTime> startTime = formulaUnoDataHandler.getRacerTimesTable(racerStartTimeList);
		LinkedHashMap<String, LocalDateTime> endTime = formulaUnoDataHandler.getRacerTimesTable(racerEndTimeList);
		LinkedHashMap<String, LinkedHashMap<String, String>> abbreviation = formulaUnoDataHandler.getRacerDataTable(racerDataList);
		LinkedHashMap<String, String> resultRace = formulaUnoDataHandler.calcolateFinalResults(endTime, startTime);
		
		String result = formulaUnoDataHandler.printResult(resultRace, abbreviation, 3);
		System.out.println(result);			
	}	
}
