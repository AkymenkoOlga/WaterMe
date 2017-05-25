package com.project.gta.demo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Paul on 25.05.2017.
 */

class FileManager {

    //region Singleton
    public static FileManager getInstance() {
        if (_instance == null)
            _instance = new FileManager();
        return _instance;
    }

    private FileManager() {}

    private static FileManager _instance = null;
    //endregion

    private String FILENAME = "Values.txt";

    void writeToFile(String text, File path){
        File file = new File(path,FILENAME);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(text.getBytes());
            stream.close();
        }
        catch(IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    String readFromFile(File path){
        File file = new File(path,FILENAME);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File read failed: " + e.toString());
        }
        return text.toString();
    }
}
