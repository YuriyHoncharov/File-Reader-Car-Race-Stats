package ua.com.foxminded.yuriy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class FileHandleTest {

	static FileHandle testFileHandle = new FileHandle();

	@Test
	void getArrayList_whenFileWasNotFound_throwException() {
		String testNonExistingFilePath = "test//nonExisting.txt";
		File testNonExistingFile = new File(testNonExistingFilePath);
		assertThrows(NoSuchFileException.class, () -> testFileHandle.getDataFromFile(testNonExistingFile));
	}

	@Test
	void getArrayList_whenFileIsEmpty_throwException() {		
		String testFilePath = "test//testEmpty.txt";
		File testEmptyFile = new File(testFilePath);
		assertThrows(IOException.class, () -> testFileHandle.getDataFromFile(testEmptyFile));
	}

	@Test
	void getArrayList_whenFileHasOneLine_returnListOfString() throws IOException {		
		String testFileOneLineString = "test//testStartOneLine.txt";
		File endTestFileOneLine = new File(testFileOneLineString);
		List<String> expected = Arrays.asList("SVF2018-05-24_12:02:58.917");
		List<String> actual = testFileHandle.getDataFromFile(endTestFileOneLine);
		assertEquals(expected, actual);
		
	}
}
