package com.williamHill.fileSearch.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;


import com.williamHill.fileSearch.FindFile;


public class FindFileTest {

    @Test
    public void testGetFileCount() throws IOException {
          
          Path startingDir = Paths.get("directory");

          FindFile finder0 = new FindFile("pdfFile_2.2.pdf");
          Files.walkFileTree(startingDir, finder0);
          assertEquals("File (pdfFile_2.2.pdf) counter error", finder0.getFileCount(), 1);
          
          FindFile finder1 = new FindFile("*.txt"); 
          Files.walkFileTree(startingDir, finder1);
          assertEquals("File (*.txt) counter error", finder1.getFileCount(), 7);
          
          FindFile finder2 = new FindFile("examp*");
          Files.walkFileTree(startingDir, finder2);
          assertEquals("File (examp*) counter error", finder2.getFileCount(), 1);
          
          FindFile finder3 = new FindFile("file_1.[01].*");
          Files.walkFileTree(startingDir, finder3);
          assertEquals("File (file_1.[01].*) counter error", finder3.getFileCount(), 3);
     }

     @Test
     public void testGetFilesList() throws IOException {
    	 
         Path startingDir = Paths.get("directory");
         
         FindFile finder0 = new FindFile("pdfFile_2.2.pdf");
         Files.walkFileTree(startingDir, finder0);
         assertEquals("File (pdfFile_2.2.pdf) get Files error", 
        		 finder0.getFilesList().get(0), Paths.get("directory\\directory2\\pdfFile_2.2.pdf"));
         
         FindFile finder1 = new FindFile("JUnit*");
         Files.walkFileTree(startingDir, finder1);
         assertEquals("File (JUnit*) get Files error", 
        		 finder1.getFilesList().get(1), Paths.get("directory\\directory2\\directory_2.3\\directory_2.3.1\\JUnit_1.2.png"));
         
         FindFile finder2 = new FindFile("*_[012].[012].*");
         Files.walkFileTree(startingDir, finder2);
         assertEquals("File (*_[012].[012].*) get Files error", 
        		 finder2.getFilesList().get(3), Paths.get("directory\\directory2\\directory_2.3\\directory_2.3.1\\JUnit_1.2.png"));
         assertEquals("File (*_[012].[012].*) get Files error", 
        		 finder2.getFilesList().get(5), Paths.get("directory\\directory2\\word_2.1.docx"));
         
         FindFile finder3 = new FindFile("*[pn]F*");
         Files.walkFileTree(startingDir, finder3);
         assertEquals("File (*[pn]F*) get Files error", 
        		 finder3.getFilesList().get(0), Paths.get("directory\\directory2\\directory_2.3\\directory_2.3.2\\JasonFile.json"));
         assertEquals("File (*[pn]F*) get Files error", 
        		 finder3.getFilesList().get(1), Paths.get("directory\\directory2\\directory_2.3\\directory_2.3.2\\JspFile.jsp"));
     }

}
