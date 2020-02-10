package com.epita.project.service.transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FiletransactionDAO {
	BufferedWriter bw = null ;
	File file = new File("saveOutput.txt");
// write one file into
	public void write(String s ) throws IOException {
		
		FileWriter fw = new FileWriter(file,true);
		  bw = new BufferedWriter(fw);
		  bw.write(s);
		  bw.write("\n");
		  bw.close();
		  fw.close();
		
	}


}
