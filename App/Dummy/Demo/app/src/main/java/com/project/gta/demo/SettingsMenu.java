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

public class SettingsMenu extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public Switch SWsounds;
    public Switch SWled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        //Button definition for this class
        Button raspberrywifiB = (Button) findViewById(R.id.BTNraspberrywifi);
        Button bluetoothB = (Button) findViewById(R.id.BTNbluetooth);
        SWled = (Switch) findViewById(R.id.SWled);
        Switch SWnotification = (Switch) findViewById(R.id.SWnotifications);
        SWsounds = (Switch) findViewById(R.id.SWsounds);
        //=====================================


        /* dem Button muss gesagt werden, dass er die laufende Activity (MainMenu) als seinen
        OnClickListener verwendet */

        //SetOnListener
        raspberrywifiB.setOnClickListener(this); //this = Refernez aufs aktuelle Object -> die laufende Activity
        bluetoothB.setOnClickListener(this);
        SWled.setOnCheckedChangeListener(BluetoothAdministration.getInstance(this));
        SWnotification.setOnCheckedChangeListener(this);
        SWsounds.setOnCheckedChangeListener(BluetoothAdministration.getInstance(this));
        //===============================


//        //Disable buttons if Bluetooth not enabled
//        BluetoothAdapter BA = BluetoothAdministration.getInstance(this).BA;
//        boolean hasBluetooth = BluetoothAdministration.getInstance(this).hasBluetooth;
//        if (hasBluetooth && !BA.isEnabled()) {
//            SWsounds.setEnabled(false);
//            SWled.setEnabled(false);
//
//        } else {
//            SWsounds.setEnabled(true);
//            SWled.setEnabled(true);
//        }
//        //============================================

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.BTNraspberrywifi):
                startActivity(new Intent(this, WifiMenu.class));
                break;
            case (R.id.BTNbluetooth):
                startActivity(new Intent(this, BluetoothMenu.class));
                break;
        }
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
        }
    }
}