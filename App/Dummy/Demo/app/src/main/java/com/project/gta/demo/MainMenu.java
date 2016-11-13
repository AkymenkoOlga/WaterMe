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
        Button button = (Button) findViewById(R.id.settings);

        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        button.setOnClickListener(this); //this = Refernez aufs aktuelle Object -> die laufende Activity
    }
    @Override
    public void onClick(View v) {
    startActivity(new Intent(this,settings.class));
        /* Intent-Object definiert, welche Klasse Android
        für die zu startende Activity verwenden soll*/
    }
}
