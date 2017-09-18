package com.plexonic.rest;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UserRepository {
    private static Map<String, String> userMap = new HashMap<String, String>();
    private static Map<String, String> requestMap = new HashMap<String, String>();

    public UserRepository() {
    }

    public void csvToDBfromZip() {
        try {
            ZipFile zipFile = null;
            ClassLoader classLoader = getClass().getClassLoader();
            zipFile = new ZipFile(classLoader.getResource("data.zip").getFile());
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"));
                String[] nextLine;
                reader.readNext();  ///escape csv titles
                if (entry.getName().equals("users.csv")) {

                    while ((nextLine = reader.readNext()) != null) {
                        userMap.put(nextLine[0], nextLine[1]);
                        insertModel(nextLine[0], nextLine[1], false);
                    }
                }
                else if (entry.getName().equals("requests.csv")){
                    while ((nextLine = reader.readNext()) != null) {
                        requestMap.put(nextLine[0], nextLine[1]);
                        insertModel(nextLine[0], nextLine[1], true);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertModel(String name, String date, Boolean forRequest) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/plexonic", "root", "root");

                String s;
                if(forRequest) {
                    s = "insert into request values(?,?)";
                }
                else {
                    s = "insert into user values(?,?)";
                }
                PreparedStatement ps = con.prepareStatement(s);
                ps.setString(1, name);
                ps.setString(2, date.substring(1, 11));

                ps.executeUpdate();
                con.commit();

                ps.close();
                con.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();

        }
    }


    public String getUserById(String id) {
        return userMap.get(id);
    }

    public Map<String, String> findAllUser() {
        return userMap;
    }
}
