package com.project.gta.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static com.project.gta.demo.R.id.BTNgetHumidity;

public class SinglePlantMenu extends AppCompatActivity implements View.OnClickListener {

    private Button getHumidityB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plant_menu);

        SharedPreferences pref = getSharedPreferences("Plant1",0);
        setTitle(pref.getString("Plant1Name", "MyPlant"));

        Button graphB = (Button) findViewById(R.id.BTNgraph);
        getHumidityB = (Button) findViewById(BTNgetHumidity);
        getHumidityB.setOnClickListener(BluetoothAdministration.getInstance(this));
        getHumidityB.setText("N/V");
        graphB.setOnClickListener(this);
    }

    public Button getButton() {
        return getHumidityB;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNgraph:
                startActivity(new Intent(this, HumidityGraph.class));
                break;
        }
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Context has to be set again.
        getHumidityB.setOnClickListener(BluetoothAdministration.getInstance(this));
    }
}
