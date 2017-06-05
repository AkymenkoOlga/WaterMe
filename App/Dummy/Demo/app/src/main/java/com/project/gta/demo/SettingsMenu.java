package com.project.gta.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsMenu extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public Switch SWsounds;
    public Switch SWled;
    public Switch SWbluetooth;
    private BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    private final boolean hasBluetooth =!(BA == null);
    private final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        SWled = (Switch) findViewById(R.id.SWled);
        Switch SWnotification = (Switch) findViewById(R.id.SWnotifications);
        SWsounds = (Switch) findViewById(R.id.SWsounds);

        //SetOnListener
        SWled.setOnCheckedChangeListener(BluetoothAdministration.getInstance(this));
        SWnotification.setOnCheckedChangeListener(this);
        SWsounds.setOnCheckedChangeListener(BluetoothAdministration.getInstance(this));

        //===============================
        SWbluetooth = (Switch) findViewById(R.id.SWbluetooth);
        if(hasBluetooth && !BA.isEnabled())
            SWbluetooth.setChecked(false);
        if(hasBluetooth && BA.isEnabled())
            SWbluetooth.setChecked(true);
        SWbluetooth.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.SWnotifications:
                if (isChecked) {
                    Toast toast_notifications_enabled = Toast.makeText
                            (getApplicationContext(), "Notifications enabled", Toast.LENGTH_LONG);
                    toast_notifications_enabled.show();
                } else {
                    Toast toast_notifications_disabled = Toast.makeText
                            (getApplicationContext(), "Notifications disabled", Toast.LENGTH_LONG);
                    toast_notifications_disabled.show();
                }
                break;
            case R.id.SWbluetooth:
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
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Context has to be set again.
        SWled.setOnClickListener(BluetoothAdministration.getInstance(this));
        SWsounds.setOnCheckedChangeListener(BluetoothAdministration.getInstance(this));
    }
}