package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataRead {

	public List<String> getArrayList(File file) {
		List<String> result = null;
		try (Stream<String> stream = Files.lines(file.toPath())) {
//			result = stream.collect(Collectors.toList());
			result = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, LocalDateTime> getTimeResult(List<String> input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		return input.stream().collect(HashMap::new,
				(map, str) -> map.put(str.substring(0, 3), LocalDateTime.parse(str.substring(3), formatter)),
				HashMap::putAll);
	}

	public Map<String, LocalDateTime> calcolateResult(Map<String, LocalDateTime> endTime,
			Map<String, LocalDateTime> startTime) {
		

		

		return startTime;
	}

}
