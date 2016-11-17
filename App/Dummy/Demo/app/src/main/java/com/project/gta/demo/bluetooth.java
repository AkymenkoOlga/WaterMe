package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;

public class bluetooth extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private BluetoothAdapter BA;
    private final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Switch bluetoothSw = (Switch)findViewById(R.id.bluetoothsw);
        bluetoothSw.setOnCheckedChangeListener(this);

        BA = BluetoothAdapter.getDefaultAdapter();

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
}
