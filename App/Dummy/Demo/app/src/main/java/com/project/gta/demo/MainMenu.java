package com.project.gta.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    /* Erläuterung: Interface OnClickListener definiert Methode OnClick(),
    deshalb muss in dieser Klasse die Methode onClick() implementiert werden */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button settingsB = (Button) findViewById(R.id.BTNsettings);
        Button helpB = (Button) findViewById(R.id.BTNhelp);
        Button aboutB = (Button) findViewById(R.id.BTNabout);
        Button myplantsB = (Button) findViewById(R.id.BTNmyplants);
        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        settingsB.setOnClickListener(this); //this = Referenz aufs aktuelle ObjeKt -> die laufende Activity#
        helpB.setOnClickListener(this);
        aboutB.setOnClickListener(this);
        myplantsB.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.BTNsettings:
                startActivity(new Intent(this,settings.class));
        /* Intent-Object definiert, welche Klasse Android
        für die zu startende Activity verwenden soll*/
                break;
            case R.id.BTNhelp:
                startActivity(new Intent(this,help_menu.class));
                break;
            case R.id.BTNabout:
                startActivity(new Intent(this,about.class));
                break;
            case R.id.BTNmyplants:
                startActivity(new Intent(this,PlantMenu.class));
                break;
        }
    }
}