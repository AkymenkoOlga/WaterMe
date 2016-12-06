package com.project.gta.demo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class HelpMenu extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);


        Button BTNhelpBluetoothPi = (Button) findViewById(R.id.BTNhelpBluetoothPi);
        BTNhelpBluetoothPi.setOnClickListener(this);

        Button BTNhelpInstallPi = (Button) findViewById(R.id.BTNhelpInstallPi);
        BTNhelpInstallPi.setOnClickListener(this);

        Button BTNhelpAddPlant = (Button) findViewById(R.id.BTNhelpAddPlant);
        BTNhelpAddPlant.setOnClickListener(this);

        Button BTNhelpAskUs = (Button) findViewById(R.id.BTNhelpAskus);
        BTNhelpAskUs.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.BTNhelpBluetoothPi):
                setContentView(R.layout.help_bluetooth_pi);
                break;
            case (R.id.BTNhelpInstallPi):
                setContentView(R.layout.help_install_pi);
                break;
            case (R.id.BTNhelpAddPlant):
                setContentView(R.layout.help_add_plant);
                break;
            case (R.id.BTNhelpAskus):
                startActivity(new Intent(this, WVaskus.class));
                break;
        }
    }

}


