package com.project.gta.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PlantMenu extends AppCompatActivity {
    ListView list;
    String[] web = {
            "Ficus",
            "Areca Palm",
            "Snake Plant",
            "Peace Lily",
            "Orchid",
            "Anthurium",

    } ;
    Integer[] imageId = {
            R.drawable.ficus,
            R.drawable.arecapalm,
            R.drawable.snakeplant,
            R.drawable.peacelily,
            R.drawable.orhid,
            R.drawable.anthurium

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_menu);

        PlantList adapter = new
                PlantList(PlantMenu.this, web, imageId);
        list=(ListView)findViewById(R.id.LSTplants);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(PlantMenu.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}