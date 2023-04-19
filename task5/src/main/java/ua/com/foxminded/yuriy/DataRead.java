package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRead {

	private List<String> getArrayList(File file) {
		List<String> result = null;
		try (Stream<String> stream = Files.lines(file.toPath())) {
			result = stream.sorted().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getAbbreviation(File file) {
		List<String> tempList = getArrayList(file);
		return tempList.stream().sorted(Comparator.comparing(str -> str.split("_")[0])).collect(LinkedHashMap::new,
				(map, str) -> {
					String[] data = str.split("_");
					String abbreviationRacer = data[0];
					String abbreviationRacerName = data[1];
					String abbreviationRacerTeam = data[2];
					map.computeIfAbsent(abbreviationRacer, k -> new LinkedHashMap<>()).put(abbreviationRacerName,
							abbreviationRacerTeam);
				}, LinkedHashMap::putAll);
	}

	public LinkedHashMap<String, LocalDateTime> getTimeResult(File file) {
		List<String> input = getArrayList(file);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		return input.stream().sorted().collect(LinkedHashMap::new,
				(map, str) -> map.put(str.substring(0, 3), LocalDateTime.parse(str.substring(3), formatter)),
				LinkedHashMap::putAll);
	}

	public LinkedHashMap<String, String> calcolateResult(LinkedHashMap<String, LocalDateTime> endTime,
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
	
	public String printResult (LinkedHashMap<String, String> results, LinkedHashMap<String, LinkedHashMap<String, String>> driverData, int topResultsNumber ) {
		StringBuilder resultPrint = new StringBuilder();
		AtomicInteger count = new AtomicInteger(1);
		results.entrySet().stream()
		.sorted(Map.Entry.comparingByValue())
		.limit(topResultsNumber)
		.forEachOrdered(entry -> {
			String driverAbbviation = entry.getKey();
			String time = entry.getValue();
			LinkedHashMap<String, String> driverNameAndTeam = driverData.get(driverAbbviation);
			String driverName = driverNameAndTeam.keySet().iterator().next();
			String driverTeam = driverNameAndTeam.values().iterator().next();
			String outTopResults = String.format("%-2d. %-20s | %-30s | %s\n",
					count.getAndIncrement(),
					driverName,
					driverTeam,
					time);
			resultPrint.append(outTopResults);
	}
		);
	resultPrint.append("---------------------------------\n");
				
		results.entrySet().stream()
		.sorted(Map.Entry.comparingByValue())
		.skip(topResultsNumber)
		.forEachOrdered(entry -> {
			String driverAbbviation = entry.getKey();
			String time = entry.getValue();
			LinkedHashMap<String, String> driverNameAndTeam = driverData.get(driverAbbviation);
			String driverName = driverNameAndTeam.keySet().iterator().next();
			String driverTeam = driverNameAndTeam.values().iterator().next();
			String outTopResults = String.format("%-2d. %-20s | %-30s | %s\n",
					count.getAndIncrement(),
					driverName,
					driverTeam,
					time);
			resultPrint.append(outTopResults);
		});
		return resultPrint.toString();
	}
}

