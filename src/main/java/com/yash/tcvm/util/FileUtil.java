package com.yash.tcvm.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> readFile(String filePath) {
		List<String> linesInFile = new ArrayList<String>();

		BufferedReader bufferedReader = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);
			String currentLine;
			while ((currentLine = bufferedReader.readLine()) != null) {
				linesInFile.add(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return linesInFile;

	}

}
