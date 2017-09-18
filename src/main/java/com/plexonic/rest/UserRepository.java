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

public class EmployeeRepository {
	private static Map<String,String> regMap = new HashMap<String,String>();
	private static Map<String,String> activeMap = new HashMap<String,String>();
	public EmployeeRepository(){
//		map.put("111", new Employee(111, "Ram", "ABC"));
//		map.put("222", new Employee(222, "Shyam", "EFG"));
//		map.put("333", new Employee(333, "Mohan", "XYZ"));

		try {
			ZipFile zipFile = null;
			ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource("file/test.xml").getFile());
			zipFile = new ZipFile(classLoader.getResource("data.zip").getFile());
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while(entries.hasMoreElements()){
				ZipEntry entry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(entry);


				CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
//				new FileReader("users.csv")
				String [] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					// nextLine[] is an array of values from the line
//					System.out.println(nextLine[0] + nextLine[1] );
							regMap.put(nextLine[0], nextLine[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public  String getEmployeeById(String id){
		return regMap.get(id);
	}
	public  Map<String,String> findAllEmployee(){
		return regMap;
	}
}
