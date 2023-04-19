
package ua.com.foxminded.yuriy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		Path path = Paths.get("C:\\repository\\task5\\task5\\src\\");
		String startLogPath = "\\start.log";
		String endLogPath = "\\end.log";
		String abbreviationLogPath = "\\abbreviations.txt";

		File fileStart = new File(path + startLogPath);
		File fileEnd = new File(path + endLogPath);
		File fileAbbreviation = new File(path + abbreviationLogPath);		
			
		DataRead formulaUno = new DataRead();
				
		LinkedHashMap<String, LocalDateTime> startTime = formulaUno.getTimeResult(fileStart);
		LinkedHashMap<String, LocalDateTime> endTime = formulaUno.getTimeResult(fileEnd);
		LinkedHashMap<String, LinkedHashMap<String, String>> abbreviation = formulaUno.getAbbreviation(fileAbbreviation);
		LinkedHashMap<String, String> resultRace = formulaUno.calcolateResult(endTime, startTime);
		
		String result = formulaUno.printResult(resultRace, abbreviation, 15);
		System.out.println(result);
		
		
		
				
}
	
}

