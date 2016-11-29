package com.project.gta.demo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    /* Erläuterung: Interface OnClickListener definiert Methode OnClick(),
    deshalb muss in dieser Klasse die Methode onClick() implementiert werden */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button settingsB = (Button) findViewById(R.id.settings);
        Button helpB = (Button) findViewById(R.id.help);
        Button aboutB = (Button) findViewById(R.id.about);
        Button myplantsB = (Button) findViewById(R.id.myplants);
        ImageView logoIV = (ImageView) findViewById(R.id.logo_main_menu);
        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        settingsB.setOnClickListener(this); //this = Referenz aufs aktuelle ObjeKt -> die laufende Activity#
        helpB.setOnClickListener(this);
        aboutB.setOnClickListener(this);
        myplantsB.setOnClickListener(this);
        logoIV.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.settings:
                startActivity(new Intent(this,settings.class));
        /* Intent-Object definiert, welche Klasse Android
        für die zu startende Activity verwenden soll*/
                break;
            case R.id.help:
                startActivity(new Intent(this,help_menu.class));
                break;
            case R.id.about:
                startActivity(new Intent(this,about.class));
                break;
            case R.id.myplants:
                startActivity(new Intent(this,PlantMenu.class));
                break;
            case R.id.logo_main_menu:
                Toast toast_waterme_logo= Toast.makeText
                        (getApplicationContext(),"WaterMe is so awesome",Toast.LENGTH_LONG);
                toast_waterme_logo.show();

        }
    }
}