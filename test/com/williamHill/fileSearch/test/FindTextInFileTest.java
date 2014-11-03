package com.williamHill.fileSearch.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Test;
import com.williamHill.fileSearch.FindTextInFile;

public class FindTextInFileTest {
 
	
	@Test
	public void getMatchedFilestest() throws FileNotFoundException {
		
		ArrayList<Path> resultList = new ArrayList<Path>();
		Path file1 = Paths.get("directory\\directory3\\testFindText1.txt");
		Path file2 = Paths.get("directory\\directory3\\testFindText2.txt");
		Path file3 = Paths.get("directory\\directory3\\testFindText3.xml");
		resultList.add(file1);
		resultList.add(file2);
		resultList.add(file3);
		String pattern = "Hello world";
		FindTextInFile f = new FindTextInFile(resultList, pattern);
		
		assertEquals("File 'directory\\directory3\\testFindText1.txt' not found", resultList.get(0),
				Paths.get("directory\\directory3\\testFindText1.txt"));
		
		assertEquals("Pattern " + pattern + " not found error", f.getMatchedFiles().get(0),
				Paths.get("directory\\directory3\\testFindText1.txt"));
		
		//matches d and f with their counterparts in abcdefg. No other matches exist.
		pattern = "[a-f&&[^a-c]&&[^e]]";
		f = new FindTextInFile(resultList, pattern);
		assertEquals("Pattern " + pattern + " not found error", f.getMatchedFiles().get(0),
				Paths.get("directory\\directory3\\testFindText1.txt"));
		
		pattern = "I Lost my:? (mind|car|cell phone|dog)";
		f = new FindTextInFile(resultList, pattern);
		assertEquals("Pattern " + pattern + " not found error", f.getMatchedFiles().get(0),
				Paths.get("directory\\directory3\\testFindText1.txt"));
		assertEquals("Pattern " + pattern + " not found error", f.getMatchedFiles().get(1),
				Paths.get("directory\\directory3\\testFindText2.txt"));
		assertEquals("Pattern " + pattern + " not found error", f.getMatchedFiles().get(2),
				Paths.get("directory\\directory3\\testFindText3.xml"));
		
		// matches a text line if this text line contains either the word "Joe" or the word "Jim" or both.
		pattern = ".*(jim|joe).*";
		f = new FindTextInFile(resultList, pattern);
		assertEquals("Pattern " + pattern + "not found error", f.getMatchedFiles().get(0),
				Paths.get("directory\\directory3\\testFindText3.xml"));

		
		//match the beginning of the line, group everything within the parenthesis as group 1,
		//match n digits, where n is a number equal to or greater than zero, optionally match a dash,
		//match the end of the line
		pattern = "^(\\d{3}-?\\d{2}-?\\d{4})$";
		f = new FindTextInFile(resultList, pattern);
		assertEquals("Pattern " + pattern + "not found error", f.getMatchedFiles().get(0),
				Paths.get("directory\\directory3\\testFindText2.txt"));
		
	}

}
