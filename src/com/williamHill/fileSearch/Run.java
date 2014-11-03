package com.williamHill.fileSearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Run {

    static void commandFormat() {
    	
    	System.err.println("The command is not recognized as valid. ");
    	System.err.println("Format of the command should be : "
    			+ "search.bat [-f ''file''] [-p ''text''] <directory>");
    	System.err.println("For help, please type: search.bat -help");
        System.exit(-1);
    }
    
    
    static void commandHelp() {
    	
    	System.err.println(String.format("%-20s%s", "search.bat:", "The batch file."));
    	System.err.println(String.format("%-20s%s", "-f:", "The -f option print to the console a list of "
    			+ "files whose names match the ''file''."));
    	System.err.println(String.format("%-20s%s", "file:", "The file that you want to search. The file can "
    			+ "also be a regular expression rather than plain text. The name of the file should be"
    			+ "enclosed in double quotes"));
    	System.err.println(String.format("%-20s%s", "-p:", "With the -p option, files that have the "
    			+ "''text'' specified by the -p option in their content will be displayed."));
    	System.err.println(String.format("%-20s%s", "text:","The ''text'' that you want to match within the file. "
    			+ "The text can also be a regular expression rather than plain text. The text should be"
    			+ "enclosed in double quotes"));
    	System.err.println(String.format("%-20s%s", "directory:","The root directory you want to traverse."));
    }
    
    private static void searchFile(String[] args) throws IOException {
    	
        Path startingDir = Paths.get(args[2]);
        String filePattern = args[1];
 
        FindFile finder = new FindFile(filePattern);     
        Files.walkFileTree(startingDir, finder);
        
        if (finder.getFileCount() == 0) {
        	System.err.println("File '" + args[1] + "' not found.");
        	System.exit(-1);
        }
        
        for (int i=0; i<finder.getFilesList().size(); i++) 
        	System.out.println(finder.getFilesList().get(i));

    }
    
    
    private static void searchTextInFile (String[] args) throws IOException {
    	
        Path startingDir = Paths.get(args[4]);
        String filePattern = args[1];
 
        FindFile finder = new FindFile(filePattern);     
        Files.walkFileTree(startingDir, finder);
        
        if (finder.getFileCount() == 0) {
        	System.err.println("File '" + args[1] + "' not found.");
        	System.exit(-1);
        }
        
        FindTextInFile textInFile = new FindTextInFile(finder.getFilesList(), args[3]);
        ArrayList<Path> result = textInFile.getMatchedFiles();
        
        if (result.size() == 0) {
        	System.out.println("No file with the specific pattern found.");
        	System.exit(0);
        }
        
        for (int i=0; i<result.size(); i++)
        	System.out.println(result.get(i));
    }
    
    
	public static void main(String[] args) throws IOException {
	 
	        if (args.length == 3 && args[0].equals("-f")) {
	        	searchFile(args);
	        }
	        else if (args.length == 5 && args[0].equals("-f") && args[2].equals("-p")) {
	        	searchTextInFile(args);
	        }
	        else if (args.length == 1 && args[0].equals("-help")) {
	        	commandHelp();
	        }
	        else {
	        	commandFormat();
	        }     
	}

	
}
