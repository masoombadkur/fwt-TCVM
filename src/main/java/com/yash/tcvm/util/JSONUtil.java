package com.yash.tcvm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yash.tcvm.exception.EmptyException;

public class JSONUtil {
	
	private static Logger logger = Logger.getLogger(JSONUtil.class);

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void writeJSONToFile(Object object, String filePath, String fileName) throws EmptyException {
		logger.info("JSONUtil's writeJSONToFile() method starts");
		
		if(filePath.isEmpty()){
			throw new EmptyException("File path is null");
		}
		
		if(fileName.isEmpty()){
			throw new EmptyException("File name is null");
		}
		
		try (FileWriter writer = new FileWriter(filePath.concat(fileName))) {
			gson.toJson(object, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String convertObjectToJSONString(Object object) {
		logger.info("JSONUtil's convertObjectToJSONString() method starts");
		
		String jsonString = gson.toJson(object);
		System.out.println(jsonString);
		return jsonString;
	}

	public static List<?> readJSONFromFile(String filePath, String fileName) throws FileNotFoundException, EmptyException {
		
		logger.info("JSONUtil's readJSONFromFile() method starts");
		
		if(filePath.isEmpty()){
			throw new EmptyException("File path is null");
		}
		
		if(fileName.isEmpty()){
			throw new EmptyException("File name is null");
		}
		
		File fileToBeRead = new File(filePath.concat(fileName));
		
		if(!fileToBeRead.exists()){
			throw new FileNotFoundException("File doesnt exist");
		}
		
		if(fileToBeRead.length() <= 0){
			throw new EmptyException("File is empty");
		}
		
		List<?> list = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			list = objectMapper.readValue(fileToBeRead, List.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static <T> T mapObjectToSpecificModelObject(Class<T> objectType, Object object) {
		logger.info("JSONUtil's mapObjectToSpecificModelObject() method starts");
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(object, objectType);
	}

}
