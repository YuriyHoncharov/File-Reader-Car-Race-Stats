package ua.com.foxminded.yuriy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandle {	
	
	// Initialize file names
	
//	private static String startLogPath = "start.log";
//	private static String endLogPath = "end.log";
//	private static  String racerDataListPath = "abbreviations.txt";
	private static String startLogPath = "start.log";
	private static String endLogPath = "end.log";
	private static  String racerDataListPath = "abbreviations.txt";
	
	// Initialize files 
	
	public static final File startDataFile = new File(startLogPath);
	public static final File endDataFile = new File(endLogPath);
	public static final File racerDataFile = new File(racerDataListPath);
	
	public List<String> getArrayList(File file) {
		
		Pattern timePatternLine = Pattern.compile("^[A-Z]{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}$");
		Pattern abbreviationPatternLine = Pattern.compile("^[A-Z]{3}_[A-Z][a-z]*( [A-Z][a-z]*)*_?[A-Z ]+$");
//		
//			
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String stringLine;
			int line = 0;
			while ((stringLine = reader.readLine()) != null) {
				line++;
				if (!timePatternLine.matcher(stringLine).matches() ||
				    !abbreviationPatternLine.matcher(stringLine).matches()) {
					throw new IllegalArgumentException("Line" + line + "does not match with the excpected format.");
				}
			}
	}
					
		List<String> result = null;
		try (Stream<String> stream = Files.lines(file.toPath())) {
			result = stream.sorted().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}		
		return result;
	}
}
