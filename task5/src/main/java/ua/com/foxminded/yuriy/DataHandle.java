package ua.com.foxminded.yuriy;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataHandle {

	public LinkedHashMap<String, LinkedHashMap<String, String>> getRacerDataTable(List<String> racerDataList) {
		Pattern abbreviationPatternLine = Pattern.compile("^[A-Z]{3}_[A-Z][a-z]*( [A-Z][a-z]*)*_?[A-Z ]+$");
		return racerDataList.stream().sorted(Comparator.comparing(str -> str.split("_")[0])).collect(LinkedHashMap::new,
				(map, str) -> {
					if(!str.matches(abbreviationPatternLine.pattern())) {
						throw new IllegalArgumentException("Invalid data format");
					}
					String[] data = str.split("_");
					if (data.length != 3) {
						throw new IllegalArgumentException("Invalid data input from list of String");
					}
					String abbreviationRacer = data[0];
					String abbreviationRacerName = data[1];
					String abbreviationRacerTeam = data[2];
					map.computeIfAbsent(abbreviationRacer, k -> new LinkedHashMap<>()).put(abbreviationRacerName,
							abbreviationRacerTeam);
				}, LinkedHashMap::putAll);
	}

	public LinkedHashMap<String, LocalDateTime> getRacerTimesTable(List<String> racerTimeList) {
		Pattern timePatternLine = Pattern.compile("^[A-Z]{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}$");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		return racerTimeList.stream().sorted().collect(LinkedHashMap::new, (map, str) -> {
			if (!str.matches(timePatternLine.pattern())) {
				throw new IllegalArgumentException("Invalid string format: " + str);
			}
			map.put(str.substring(0, 3), LocalDateTime.parse(str.substring(3), formatter));
		}, LinkedHashMap::putAll);
	}

	// Calculate result of the race based on 2 ArrayList, abbreviation as key and
	// the value as start & end time

	public LinkedHashMap<String, String> calcolateFinalResults(LinkedHashMap<String, LocalDateTime> endTime,
			LinkedHashMap<String, LocalDateTime> startTime) {
		return endTime.entrySet().stream().filter(entry -> startTime.containsKey(entry.getKey()))
				.sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, entry -> {
					Duration duration = Duration.between(startTime.get(entry.getKey()), endTime.get(entry.getKey()));
					long minutes = duration.toMinutes();
					long seconds = duration.getSeconds() % 60;
					long millis = duration.toMillis() % 1000;
					return String.format("%d:%02d.%03d", minutes, seconds, millis);
				}, (v1, v2) -> v1, LinkedHashMap::new));
	}

	public String printResult(LinkedHashMap<String, String> results,
			LinkedHashMap<String, LinkedHashMap<String, String>> driverData, int topResultsNumber) {

		// Print first "x" racers, where "x" is (int topResultNumber)

		StringBuilder resultPrint = new StringBuilder();
		AtomicInteger count = new AtomicInteger(1);
		results.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(topResultsNumber).forEachOrdered(entry -> {
			String driverAbbviation = entry.getKey();
			String time = entry.getValue();
			LinkedHashMap<String, String> driverNameAndTeam = driverData.get(driverAbbviation);
			String driverName = driverNameAndTeam.keySet().iterator().next();
			String driverTeam = driverNameAndTeam.values().iterator().next();
			String outTopResults = String.format("%-2d. %-20s | %-30s | %s\n", count.getAndIncrement(), driverName,
					driverTeam, time);
			resultPrint.append(outTopResults);
		});
		resultPrint.append("---------------------------------\n");

		// Print the rest of racers after divisor line

		results.entrySet().stream().sorted(Map.Entry.comparingByValue()).skip(topResultsNumber).forEachOrdered(entry -> {
			String driverAbbviation = entry.getKey();
			String time = entry.getValue();
			LinkedHashMap<String, String> driverNameAndTeam = driverData.get(driverAbbviation);
			String driverName = driverNameAndTeam.keySet().iterator().next();
			String driverTeam = driverNameAndTeam.values().iterator().next();
			String outTopResults = String.format("%-2d. %-20s | %-30s | %s\n", count.getAndIncrement(), driverName,
					driverTeam, time);
			resultPrint.append(outTopResults);
		});
		return resultPrint.toString();
	}
}
