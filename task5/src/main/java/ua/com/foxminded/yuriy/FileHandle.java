package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandle {

	public List<String> getDataFromFile(File file) throws IOException {

		if (!file.exists()) {
			throw new NoSuchFileException("File " + file.getAbsolutePath() + " was not found");
		}

		if (file.length() == 0) {
			throw new IOException("File is empty.");
		}
		
		try (Stream<String> stream = Files.lines(file.toPath())) {
			return stream.sorted().collect(Collectors.toList());

		} catch (IOException e) {
			throw new RuntimeException("Error during reading file: " + e.getMessage());
		}
	}
}
