package com.project.gta.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import static com.project.gta.demo.R.id.BTNgetHumidity;

public class SinglePlantMenu extends AppCompatActivity{

private  Button getHumidityB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plant_menu);


        getHumidityB= (Button) findViewById(BTNgetHumidity);
        getHumidityB.setOnClickListener(BluetoothAdministration.get_instance(this));
    }

    Button getButton(){
        return getHumidityB;
    }
}
