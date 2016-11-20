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
            "Plant1",
            "Plant2",
            "Plant3",
            "Plant4",
            "Plant5",
            "Plant6",

    } ;
    Integer[] imageId = {
            R.drawable.plant1,
            R.drawable.plant2,
            R.drawable.plant3,
            R.drawable.plant4,
            R.drawable.plant4,
            R.drawable.plant5,
            R.drawable.plant6

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