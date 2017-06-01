package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class BluetoothMenu extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    private final int REQUEST_ENABLE_BT = 1;
    public Context context;
    public Switch bluetoothSw;
    public Button BTNconnect_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final boolean hasBluetooth = !(BA == null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoot_menu);
        context=this;
        //Array für gefundene BluetoothMenu device

        //Bluetooth switch
        bluetoothSw = (Switch) findViewById(R.id.SWbluetooth);
            if(hasBluetooth && !BA.isEnabled())
                bluetoothSw.setChecked(false);
            if(hasBluetooth && BA.isEnabled())
                bluetoothSw.setChecked(true);
        bluetoothSw.setOnCheckedChangeListener(this);

        Button BTNlistpaireddevices = (Button) findViewById(R.id.BTNlistpaireddevices);
        BTNlistpaireddevices.setOnClickListener(this);
/*
        //Button for new devices
        Button BTNlistfounddevices = (Button) findViewById(R.id.BTNlistfounddevices);
        BTNlistfounddevices.setOnClickListener(this);
*/
        BTNconnect_bt = (Button) findViewById(R.id.BTNconnect_bt);
        BTNconnect_bt.setOnClickListener(BluetoothAdministration.getInstance(this));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final boolean hasBluetooth = !(BA == null);
        if(isChecked) {
            if(hasBluetooth && !BA.isEnabled())
            {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, REQUEST_ENABLE_BT);
            }
            if(hasBluetooth && BA.isEnabled()){
                Toast toast_bt_already_enabled = Toast.makeText
                        (getApplicationContext(),"Bluetooth is already enabled",Toast.LENGTH_LONG);
                toast_bt_already_enabled.show();
            }
        }
        else{
            if(hasBluetooth && BA.isEnabled())
            {
                BA.disable();
                Toast toast_bt_disabled = Toast.makeText
                        (getApplicationContext(),"Bluetooth disabled",Toast.LENGTH_LONG);
                toast_bt_disabled.show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //Button for listpaireddevices is clicked
            case R.id.BTNlistpaireddevices:
                startActivity(new Intent(this,ListPairedDevicesActivity.class));
                break;
            //Button for listfounddevices is clicked
/*            case R.id.BTNlistfounddevices:
                newdevices();
                break;
*/
        }
    }
/*
    //testfunction for discovering new devices
    public void newdevices() {
        BA.startDiscovery();
        // Create a BroadcastReceiver for ACTION_FOUND
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                   // btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        BA.cancelDiscovery();
        }
*/
    }



