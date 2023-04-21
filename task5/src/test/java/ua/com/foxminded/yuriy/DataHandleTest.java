package ua.com.foxminded.yuriy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class DataHandleTest {

	private static final DataHandle testData = new DataHandle();

	@Test
	void getRacerDataTable_whenInputFormIsWrong_throwIllegalArgumentException() {
		List<String> illegalList = Arrays.asList("SVF_Sebastian Vettel_FERRARI_nice");
		assertThrows(IllegalArgumentException.class, () -> testData.getRacerDataTable(illegalList));
	}

	@Test
	void getRacerDataTable_whenIsRight_returnRightMap() {
		List<String> rightList = Arrays.asList("SVF_Sebastian Vettel_FERRARI");
		LinkedHashMap<String, LinkedHashMap<String, String>> info = new LinkedHashMap<>();
		LinkedHashMap<String, String> inMap = new LinkedHashMap<>();
		inMap.put("Sebastian Vettel", "FERRARI");
		info.put("SVF", inMap);

		assertEquals(info, testData.getRacerDataTable(rightList));
	}

	@Test
	void getRacerTimesTable_whenInputFormatIsRight_returnRightList() {
		List<String> rightList = Arrays.asList("SVF2018-05-24_12:02:58.917");
		LinkedHashMap<String, LocalDateTime> result = new LinkedHashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		LocalDateTime dateFormat = LocalDateTime.parse("2018-05-24_12:02:58.917", formatter);
		result.put("SVF", dateFormat);
		assertEquals(result, testData.getRacerTimesTable(rightList));
	}

	@Test
	void getRacerTimesTable_whenInputFormatIsWrong_throwIllegalArgumentException() {
		List<String> rightList = Arrays.asList("SVF 2018/05/24 12:02:58.917");
		LinkedHashMap<String, LocalDateTime> result = new LinkedHashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		LocalDateTime dateFormat = LocalDateTime.parse("2018-05-24_12:02:58.917", formatter);
		result.put("SVF", dateFormat);
		assertThrows(IllegalArgumentException.class, () -> testData.getRacerTimesTable(rightList));
	}

	@Test
	void calcolateFinalResults_whenInputFormatIsRight_ReturnRightResult() {

		// First list

		LinkedHashMap<String, LocalDateTime> startList = new LinkedHashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
		LocalDateTime dateStartFormat = LocalDateTime.parse("2018-05-24_12:02:58.917", formatter);
		startList.put("SVF", dateStartFormat);

		// Second list

		LinkedHashMap<String, LocalDateTime> endList = new LinkedHashMap<>();
		LocalDateTime dateEndFormat = LocalDateTime.parse("2018-05-24_12:04:03.332", formatter);
		endList.put("SVF", dateEndFormat);

		// Result

		LinkedHashMap<String, String> resultRace = new LinkedHashMap<>();
		resultRace.put("SVF", "1:04.415");
		assertEquals(resultRace, testData.calcolateFinalResults(endList, startList));
	}

	@Test
	void printResult_whenInputIsRight_PrintRightResult() {

		int topOne = 1;

		// Result list

		LinkedHashMap<String, String> resultRace = new LinkedHashMap<>();
		resultRace.put("SVF", "1:04.415");
		resultRace.put("DRR", "1:12.013");
		resultRace.put("VBM", "1:12.434");

		// Driver data list

		LinkedHashMap<String, LinkedHashMap<String, String>> driverData = new LinkedHashMap<>();
		LinkedHashMap<String, String> inMap = new LinkedHashMap<>();

		inMap.put("Sebastian Vettel", "FERRARI");
		driverData.put("SVF", inMap);

		LinkedHashMap<String, String> inMap2 = new LinkedHashMap<>();
		inMap2.put("Daniel Ricciardo", "RED BULL RACING TAG HEUER");
		driverData.put("DRR", inMap2);

		LinkedHashMap<String, String> inMap3 = new LinkedHashMap<>();
		inMap3.put("Valtteri Bottas", "MERCEDES");
		driverData.put("VBM", inMap3);

		// Builder of result

		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append("1 . Sebastian Vettel     | FERRARI                        | 1:04.415\n");
		resultBuilder.append("---------------------------------\n");
		resultBuilder.append("2 . Daniel Ricciardo     | RED BULL RACING TAG HEUER      | 1:12.013\n");
		resultBuilder.append("3 . Valtteri Bottas      | MERCEDES                       | 1:12.434\n");

		// Expected and actual results

		String resultExpectedPrint = resultBuilder.toString();
		String resultActualPrint = testData.printResult(resultRace, driverData, topOne);
		assertEquals(resultExpectedPrint, resultActualPrint);
	}

	@Test
	void getRacerDataTable_whenInputIsIllegal_throwIllegalArgumentException() {

		List<String> wrongFormatList = Arrays.asList("SVF!_Sebastian Vettel@_FERRARI*");
		assertThrows(IllegalArgumentException.class, () -> testData.getRacerDataTable(wrongFormatList));
	}

	@Test
	void getRacerDataTable_whenInputIsMoreThanThreeInfoAboutRacer_throwIllegalArgumentException() {

		List<String> wrongFormatList = Arrays.asList("SVF_Sebastian Vettel_FERRARI_England");
		assertThrows(IllegalArgumentException.class, () -> testData.getRacerDataTable(wrongFormatList));
	}
}
