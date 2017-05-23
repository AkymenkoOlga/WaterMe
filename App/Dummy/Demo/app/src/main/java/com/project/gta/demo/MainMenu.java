package com.project.gta.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.view.View.INVISIBLE;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    /* Erläuterung: Interface OnClickListener definiert Methode OnClick(),
    deshalb muss in dieser Klasse die Methode onClick() implementiert werden */

    public int easteregg = 5;

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

       // AnalogClock  ac = (AnalogClock) findViewById(R.id.analogClock);
       // ac.setVisibility(INVISIBLE);


        //ac.setBackground( getResources().getDrawable(R.drawable.logo_waterme_300x300));

        settingsB.setOnClickListener(this); //this = Referenz aufs aktuelle ObjeKt -> die laufende Activity#
        helpB.setOnClickListener(this);
        aboutB.setOnClickListener(this);
        myplantsB.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.BTNsettings:
                startActivity(new Intent(this,SettingsMenu.class));
        /* Intent-Object definiert, welche Klasse Android
        für die zu startende Activity verwenden soll*/
                break;
            case R.id.BTNhelp:
                startActivity(new Intent(this,HelpMenu.class));
                break;
            case R.id.BTNabout:
                startActivity(new Intent(this,About.class));
                break;
            case R.id.BTNmyplants:
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.logo_main_menu:
                easteregg--;
                if(easteregg==0)
                    Easteregg();
                break;
            default: //nothing
        }
    }
    public void Easteregg(){
        ImageView logo = (ImageView) findViewById(R.id.logo_main_menu);
        logo.setVisibility(INVISIBLE);
        logo.setImageDrawable(null);

        LinearLayout ll = (LinearLayout) findViewById(R.id.layout);
        AnalogClock ac = new AnalogClock(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(ac);
        ac.setBackgroundDrawable(getResources().getDrawable(R.drawable.logo_waterme_300x300));
        ll.setOrientation(LinearLayout.VERTICAL);
    }
}