package ua.com.foxminded.yuriy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class FileHandleTest {
	
	
	String testNonExistingFilePath = "nonExisting.txt";
	String testFilePath = "test.txt";
	
	FileHandle testFileHandle = new FileHandle();
	File testNonExistingFile = new File(testNonExistingFilePath);
	File testEmptyFile = new File(testFilePath);
	

	@Test
	void getArrayList_whenFileWasNotFound_throwException() {		
	assertThrows(FileNotFoundException.class, () -> testFileHandle.getArrayList(testEmptyFile));
	}
	
	

}
