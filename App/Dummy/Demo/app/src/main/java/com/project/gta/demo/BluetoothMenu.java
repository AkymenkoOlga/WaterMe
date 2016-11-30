package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final boolean hasBluetooth = !(BA == null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoot_menu);

        //Array für gefundene BluetoothMenu devices
        ArrayAdapter<String> btArrayAdapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        //Bluetooth switch
        Switch bluetoothSw = (Switch) findViewById(R.id.SWbluetooth);
            if(hasBluetooth && !BA.isEnabled())
                bluetoothSw.setChecked(false);
            if(hasBluetooth && BA.isEnabled())
                bluetoothSw.setChecked(true);
        bluetoothSw.setOnCheckedChangeListener(this);

        //Button for list_paired_devices activity
        Button BTNlistpaireddevices = (Button) findViewById(R.id.BTNlistpaireddevices);
        BTNlistpaireddevices.setOnClickListener(this);

        //Button for new devices
        Button BTNlistfounddevices = (Button) findViewById(R.id.BTNlistfounddevices);
        BTNlistfounddevices.setOnClickListener(this);

        Button BTNconnect_bt = (Button) findViewById(R.id.BTNconnect_bt);
        BTNconnect_bt.setOnClickListener(BluetoothAdministration.get_instance());
        //Management for onClick of Bluetooth-relied buttons in "BluetoothAdministration"

        //Button for reloading activity
        Button BTNreload = (Button) findViewById(R.id.BTNreload);
        BTNreload.setOnClickListener(this);
        BTNreload.setVisibility(View.INVISIBLE);


        //Disable buttons if Bluetooth not enabled
        if(hasBluetooth && !BA.isEnabled()) {
            BTNconnect_bt.setEnabled(false);
            BTNlistfounddevices.setEnabled(false);
            BTNlistpaireddevices.setEnabled(false);

        }
        else {
            BTNconnect_bt.setEnabled(true);
            BTNlistfounddevices.setEnabled(true);
            BTNlistpaireddevices.setEnabled(true);
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final boolean hasBluetooth = !(BA == null);
        Button BTNreload = (Button) findViewById(R.id.BTNreload);
        if(isChecked) {
            if(hasBluetooth && !BA.isEnabled())
            {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, REQUEST_ENABLE_BT);
                BTNreload.setVisibility(View.VISIBLE);
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
                BTNreload.setVisibility(View.INVISIBLE);
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
            case R.id.BTNlistfounddevices:
                newdevices();
                break;
            case R.id.BTNreload:
                ReloadActivity();
        }
    }
    public void ReloadActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

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

    }



