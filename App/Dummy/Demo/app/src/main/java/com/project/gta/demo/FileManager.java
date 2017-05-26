package com.project.gta.demo;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Paul on 25.05.2017.
 */

class FileManager extends AppCompatActivity {

    private GraphViewManager graphViewManager = GraphViewManager.getInstance();
    private String FILENAME = "Values.txt";

    //region Singleton
    private static FileManager _instance = null;
    public static FileManager getInstance() {
        if (_instance == null)
            _instance = new FileManager();
        return _instance;
    }
    private FileManager() {}
    //endregion

    public void writeToFile(String text, File path){
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

    public String readData(File path){
        File file = new File(path,FILENAME);
        StringBuilder text = new StringBuilder();
        graphViewManager.NumerOfValues = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                graphViewManager.extractData(line);
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
