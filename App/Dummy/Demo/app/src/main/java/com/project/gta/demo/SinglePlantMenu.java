package com.project.gta.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.project.gta.demo.R.id.BTNgetHumidity;

public class SinglePlantMenu extends AppCompatActivity implements View.OnClickListener {

    private Button getHumidityB;
    public TextView lastUpdatedTxt;
    private Plant plant;
    private ImageView image;
    public int sensorId;
    //PlantSelect ps = new PlantSelect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plant_menu);
        //mPrefs = getPreferences(MODE_PRIVATE);
        image = (ImageView) findViewById(R.id.imgPlantBig);
        Gson gson = new Gson();
        String json;
        sensorId = PlantSelect.id;

        switch(PlantSelect.id){
            case 1:
                json = PlantSelect.mPrefs.getString("plant1", "");
                plant = gson.fromJson(json, Plant.class);

                break;
            case 2:
                json = PlantSelect.mPrefs.getString("plant2", "");
                plant = gson.fromJson(json, Plant.class);
                break;
            case 3:
                json = PlantSelect.mPrefs.getString("plant3", "");
                plant = gson.fromJson(json, Plant.class);
                break;
            case 4:
                json = PlantSelect.mPrefs.getString("plant4", "");
                plant = gson.fromJson(json, Plant.class);
                break;
        }
        setTitle(plant.name);
        image.setImageBitmap(BitmapFactory.decodeFile(plant.plantImagePath));

        lastUpdatedTxt = (TextView) findViewById(R.id.TXTlastUpdate);
        Button graphB = (Button) findViewById(R.id.BTNgraph);
        getHumidityB = (Button) findViewById(BTNgetHumidity);
        getHumidityB.setOnClickListener(BluetoothAdministration.getInstance(this));
        TextView sensorIntTv = (TextView) findViewById(R.id.TXTsensorInt);
        sensorIntTv.setText("Sensor Interface: A" + String.valueOf(sensorId - 1));
        graphB.setOnClickListener(this);
        readData();
    }

    public Button getButton() {
        return getHumidityB;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNgraph:
                startActivity(new Intent(this, HumidityGraph.class));
                break;
        }
    }

    public void setColor(String s) {

    int intData = Integer.parseInt(s);
    Drawable green = getResources().getDrawable(R.drawable.buttonshape_green);
    Drawable yellow = getResources().getDrawable(R.drawable.buttonshape_yellow);
    Drawable red = getResources().getDrawable(R.drawable.buttonshape_red);

    if(intData >= 60)
    {
        getButton().setBackgroundDrawable(green); //grün
    }

    if(intData < 60 && intData > 20)

    {
       getButton().setBackgroundDrawable(yellow);//gelb
    }
    if(intData <= 20)
    {
        getButton().setBackgroundDrawable(red); //rot
    }

}

    public void readData() {
        final String id = String.valueOf(sensorId);
        try (FileInputStream fis = openFileInput("CurVal" + id + ".txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String s;
            if((s = br.readLine())!=null) {
                lastUpdatedTxt.setText("Last Update: " + s);
            }
            else {
                lastUpdatedTxt.setText("Last Update: N/V");
            }

            if((s = br.readLine())!=null) {
                getHumidityB.setText(s + "%");
                setColor(s);
            }
            else{
                getHumidityB.setText("N/V");
            }
        } catch (IOException t) {
            Log.e("", "load()", t);
        }

    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Context has to be set again.
        getHumidityB.setOnClickListener(BluetoothAdministration.getInstance(this));
        //readData();

    }
}
