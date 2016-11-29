package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.Set;

public class settings extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button raspberrywifiB = (Button) findViewById(R.id.raspberrywifi);
        Button bluetoothB = (Button) findViewById(R.id.bluetooth);
        Switch ledSW = (Switch) findViewById(R.id.SWled);

        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        raspberrywifiB.setOnClickListener(this); //this = Refernez aufs aktuelle Object -> die laufende Activity
        bluetoothB.setOnClickListener(this);
        ledSW.setOnCheckedChangeListener(BluetoothVerwaltung.get_instance());
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.raspberrywifi):
                startActivity(new Intent(this, raspberrywifi.class));
                break;
            case (R.id.bluetooth):
                startActivity(new Intent(this, bluetooth.class));
                break;
        }
    }
}