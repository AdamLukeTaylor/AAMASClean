/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aamasclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class AAMASClean
{

      /**
       * @param args the command line arguments
       */
      public static void main(final String[] args)
      {
	    for (int a = 0; a < args.length; a++)
	    {

		  System.out.println(args[a] + " =arg " + a);
	    }
	    File outputDir = new File(args[0]);
	    boolean mkdirs = outputDir.mkdirs();
	    if (outputDir.isDirectory() == false)
	    {
		  System.out.println("isn't a dir");
	    }
	    if (outputDir.canWrite() == false)
	    {
		  System.out.println("cant write");
	    }
	    if (outputDir.exists() == false)
	    {
		  System.out.println("doesnt exsist");
	    }
	    if (outputDir.isFile() == true)
	    {
		  System.out.println("is a file");
	    }
	    if (mkdirs == false && outputDir.exists() == false)
	    {//if not able to mkdir and its not a thing already
		  System.out.println("failed to mkdirs for output");
	    }
	    else
	    {
		  File[] oldFiles = outputDir.listFiles();//clean any old data
		  for (int a = 0; a < oldFiles.length; a++)
		  {
			boolean deleted = oldFiles[a].delete();
			if (deleted == false)
			{
			      System.out.println("Failed to delete " + oldFiles[a].getName());
			}
		  }
		  File workingDir = new File("C:\\Users\\Adam\\Documents\\NetBeansProjects\\AMAAS\\");
		  File[] filesToMove;
		  FileFilter filter = new FileFilter()
		  {//if is one of my stats files
			@Override
			public boolean accept(File pathname)
			{
			      return (pathname.getAbsolutePath().contains(".stats")) && (pathname.getAbsolutePath().contains("." + args[1] + "."));
			}
		  };
		  if (workingDir.isDirectory())
		  {
			filesToMove = workingDir.listFiles(filter);

			for (File filesToMove1 : filesToMove)
			{
			      //for all files
			      System.out.print("Moving " + filesToMove1.getName() + "\n");
			      if (filesToMove1.canRead() == false)
			      {
				    System.out.println("Cant read");
			      }
			      if (filesToMove1.isFile() == false)
			      {
				    System.out.println("isnt file");
			      }
			      if (filesToMove1.exists() == false)
			      {
				    System.out.println("doesnt exsist");
			      }
			      boolean moved = true;
			      //move the file
			      try
			      {
				    String outputFileName = filesToMove1.getName();
				    outputFileName = outputFileName.replace("." + args[1] + ".stats", "");//drop the placeholder
				    File outputFile = new File(outputDir + "\\" + outputFileName);
				    boolean createNewFile = outputFile.createNewFile();
				    if (createNewFile == false)
				    {
					  System.out.println("failed to creat the new file");
				    }
				    else
				    {
					  copyFile(filesToMove1, outputFile);
				    }
			      }
			      catch (IOException e)
			      {
				    moved = false;
				    System.out.println("Failed to move " + filesToMove1.getName());
			      }
			      if (moved)
			      {
				    //if it worked get rid of origional
				    filesToMove1.delete();
			      }
			}
		  }
		  else
		  {
			System.out.println("No Files to move");
		  }
	    }
	    System.out.print("Worked in java\n");
      }

      private static void copyFile(File source, File dest)
      {
	    try
	    {
		  FileInputStream fis = new FileInputStream(source);

		  BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		  FileWriter fstream = new FileWriter(dest, true);
		  BufferedWriter out = new BufferedWriter(fstream);

		  String aLine;
		  while ((aLine = in.readLine()) != null)
		  {
			//Process each line and add output to Dest.txt file
			out.write(aLine);
			out.newLine();
		  }

		  // do not forget to close the buffer reader
		  in.close();

		  // close buffer writer
		  out.close();
	    }
	    catch (Exception ex)
	    {
		  System.out.println("Failed to move files");
	    }
      }
}
