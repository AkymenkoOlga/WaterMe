package com.project.gta.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class WifiMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_menu);
    }
    public void onButtonTapSubmit(View V)   //Displays a toast(message) on the bottom of the display
    {
        Toast myToast = Toast.makeText
                (getApplicationContext(),"Submitting data...",Toast.LENGTH_LONG);
        myToast.show();
    }
}
