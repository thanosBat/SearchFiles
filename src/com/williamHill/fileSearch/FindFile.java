package com.williamHill.fileSearch;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FindFile extends SimpleFileVisitor<Path> {

	private PathMatcher match;
	private int fileFoundCount = 0;
	private ArrayList<Path> FilesList = new ArrayList<Path>();

	
	public FindFile(String pattern){
		
		match = FileSystems.getDefault().getPathMatcher("glob:" + pattern) ;
	}
	
	
	public int getFileCount() {
		
		return fileFoundCount;
	}	
	
	
	private void findGivenFile(Path file) {
		
        Path FileName = file.getFileName();
        if (FileName != null && match.matches(FileName)) {
            FilesList.add(file);
            fileFoundCount ++;
        }
    }
	
	
	public ArrayList<Path> getFilesList() {
		
		return FilesList;		
	}
	
	
	@Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		
         findGivenFile(file);
         return FileVisitResult.CONTINUE;
    }
	
	
	@Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
	
		if (exc instanceof AccessDeniedException) {
			System.err.println("Access in " + file + " denied,  typically due to a "
					+ "file permission or other access check.");
		}
		else if (exc instanceof NoSuchFileException) {
			System.err.println("The directory " + file + " does not exist.");
		}
		else {
			System.err.println("An unknown expetion occured when an access to  " + file + " attempted.");
		}
		
        return FileVisitResult.CONTINUE;
    }
	
	
}
