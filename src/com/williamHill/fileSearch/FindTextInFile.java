package com.williamHill.fileSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindTextInFile {
	
	private ArrayList<Path> list ;
	private String pattern;
	
	public FindTextInFile(ArrayList<Path> pathList, String str) {
		
		this.list = pathList;
		this.pattern = str;
	}
	
	
	public void handleExceptions(Exception e) {
		
		if (e instanceof FileNotFoundException) {
			System.err.println("File not found: " + e.getMessage());
			System.exit(-1);
		}
		else if (e instanceof NullPointerException) {
			System.err.println("Null Poiinter Exception: " + e.getMessage());
			System.exit(-1);
		}
		else {
			System.err.println("General I/O exception: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	
	public ArrayList<Path> getMatchedFiles() {
		
		ArrayList<Path> resultList = new ArrayList<Path>();
		
			for (int i=0; i<this.list.size(); i++) {
				String path = this.list.get(i).toString();
				Scanner fileScanner = null;
				
				try {
					fileScanner = new Scanner(new File(path));
					Pattern pattern =  Pattern.compile(this.pattern);
					Matcher matcher = null; 
			
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();  
						matcher = pattern.matcher(line);  
						if(matcher.find()) {
							resultList.add(this.list.get(i));
							break;
						}
					}
				}catch (Exception e) {
					handleExceptions(e);
				}finally {
					fileScanner.close();
				}
			}	
			
		return resultList;
	}

}
