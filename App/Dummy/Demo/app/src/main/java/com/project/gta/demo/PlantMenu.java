package com.project.gta.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

public class PlantMenu extends Activity {
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

        CustomList adapter = new
                CustomList(PlantMenu.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
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