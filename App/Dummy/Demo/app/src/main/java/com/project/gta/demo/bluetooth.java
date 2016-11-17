package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class bluetooth extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private BluetoothAdapter BA;
    private final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Switch bluetoothSw = (Switch) findViewById(R.id.bluetoothsw);
        bluetoothSw.setOnCheckedChangeListener(this);

        //Button o list_paired_devices activity
        Button listpaireddevicesB = (Button) findViewById(R.id.listpaireddevices);
        //Bluetooth adapter
        BA = BluetoothAdapter.getDefaultAdapter();

        listpaireddevicesB.setOnClickListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean hasBluetooth = !(BA == null);
        if(isChecked) {
            if(hasBluetooth && !BA.isEnabled())
            {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, REQUEST_ENABLE_BT);
            }
        }
        else{
            if(hasBluetooth && BA.isEnabled())
            {
                BA.disable();
                Toast myToast = Toast.makeText
                        (getApplicationContext(),"Bluetooth disabled",Toast.LENGTH_LONG);
                myToast.show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,ListPairedDevicesActivity.class));
    }
}


