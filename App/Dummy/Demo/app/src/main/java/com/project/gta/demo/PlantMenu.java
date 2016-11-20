package com.project.gta.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

public class PlantMenu extends Activity {
    ListView list;
    String[] plants = {
            "Plant1",
            "Plant2",
            "Plant3",
            "Plant4",
            "Plant5"

    } ;
    Integer[] imageId = {
            R.drawable.plant1,
            R.drawable.plant2,
            R.drawable.plant3,
            R.drawable.plant4,
            R.drawable.plant5


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_menu);

        PlantList adapter = new
                PlantList(PlantMenu.this, plants, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(PlantMenu.this, "You Clicked at " +plants[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}
