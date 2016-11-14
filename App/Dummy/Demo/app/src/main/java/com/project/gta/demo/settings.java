package com.project.gta.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button button = (Button) findViewById(R.id.raspberrywifi);

        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        button.setOnClickListener(this); //this = Refernez aufs aktuelle Object -> die laufende Activity
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, raspberrywifi.class));
        /* Intent-Object definiert, welche Klasse Android
        für die zu startende Activity verwenden soll*/
    }
}