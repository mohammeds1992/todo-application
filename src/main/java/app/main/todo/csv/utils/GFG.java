package app.main.todo.csv.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GFG { 
    public static void main(String[] args) 
        throws IOException 
    { 
  
        // create object of Path 
        Path path = Paths.get("AmanCV.docx"); 
  
        // call getFileName() and get FileName path object 
        Path fileName = path.getFileName(); 
        
        System.out.println(path.getParent().toString());
  
        // print FileName 
        System.out.println("FileName: "
                           + fileName.toString()); 
    } 
} 