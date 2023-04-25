
package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		
		DataHandle formulaUnoDataHandler = new DataHandle();
		FileHandle filesHandler = new FileHandle();
		
		File startLog = new File("start.log");
		File endLog = new File("end.log");
		File racerData = new File("abbreviations.txt");
		
		List<String> racerDataList = filesHandler.getDataFromFile(racerData);
		List<String> racerStartTimeList = filesHandler.getDataFromFile(startLog);
		List<String> racerEndTimeList = filesHandler.getDataFromFile(endLog);			
				
		LinkedHashMap<String, LocalDateTime> startTime = formulaUnoDataHandler.getRacerTimesTable(racerStartTimeList);
		LinkedHashMap<String, LocalDateTime> endTime = formulaUnoDataHandler.getRacerTimesTable(racerEndTimeList);
		LinkedHashMap<String, LinkedHashMap<String, String>> abbreviation = formulaUnoDataHandler.getRacerDataTable(racerDataList);
		LinkedHashMap<String, String> resultRace = formulaUnoDataHandler.calcolateFinalResults(endTime, startTime);
		
		String result = formulaUnoDataHandler.printResult(resultRace, abbreviation, 3);
		System.out.println(result);			
	}	
}
