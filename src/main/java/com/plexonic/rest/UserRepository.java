package com.plexonic.rest;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UserRepository {
	private static Map<String,String> regMap = new HashMap<String,String>();
	private static Map<String,String> activeMap = new HashMap<String,String>();

	public UserRepository(){}

	public void csvToDBfromZip() {
		try {
			ZipFile zipFile = null;
			ClassLoader classLoader = getClass().getClassLoader();
			zipFile = new ZipFile(classLoader.getResource("data.zip").getFile());
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while(entries.hasMoreElements()){
				ZipEntry entry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(entry);
				CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
				String [] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					regMap.put(nextLine[0], nextLine[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public  String getUserById(String id){
		return regMap.get(id);
	}

	public  Map<String,String> findAllUser(){
		return regMap;
	}
}
