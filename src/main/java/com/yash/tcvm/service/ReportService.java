package com.yash.tcvm.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.yash.tcvm.exception.EmptyException;

public interface ReportService {
	
	void generateContainerStatusReport() throws FileNotFoundException, EmptyException, IOException;
	
	void generateDrinkWiseCupCostReport() throws FileNotFoundException, EmptyException, IOException;

}
