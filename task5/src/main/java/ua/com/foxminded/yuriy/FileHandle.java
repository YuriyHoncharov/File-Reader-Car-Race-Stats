package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandle {

	// Initialize file names

	private static String startLogPath = "start.log";
	private static String endLogPath = "end.log";
	private static String racerDataListPath = "abbreviations.txt";

	// Initialize files

	public static final File startDataFile = new File(startLogPath);
	public static final File endDataFile = new File(endLogPath);
	public static final File racerDataFile = new File(racerDataListPath);

	public List<String> getDataFromFile(File file) throws IOException {

		if (!file.exists()) {
			throw new NoSuchFileException("File " + file.getAbsolutePath() + " was not found");
		}

		if (file.length() == 0) {
			throw new IOException("File is empty.");
		}

		List<String> result = new ArrayList<>();
		try (Stream<String> stream = Files.lines(file.toPath())) {
			return result = stream.sorted().collect(Collectors.toList());

		} catch (IOException e) {
			throw new RuntimeException("Error during reading file: " + e.getMessage());
		}
	}
}
