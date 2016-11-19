package com.project.gta.demo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.project.gta.demo.R.styleable.View;

/**
 * Created by TInf on 19.11.2016.
 */

public class MainActivity extends Activity {
    private ListView lvPlant;
    private plantListAdapter adapter;
    private List<Plant> mPlantList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_plant_menu);

        lvPlant = (ListView) findViewById(R.id.listview_product);

        mPlantList = new ArrayList<>();
        //add sample Data for List
        mPlantList.add(new Plant(1, "Orchid"));
        mPlantList.add(new Plant(2, "Rose"));
        mPlantList.add(new Plant(3, "Geranium"));
        mPlantList.add(new Plant(4, "Aloe"));
        mPlantList.add(new Plant(5, "Rubber Tree"));

        // Init adapter
        adapter = new plantListAdapter(getApplicationContext(), mPlantList);
        lvPlant.setAdapter(adapter);

        lvPlant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                //do something
                //Ex: display message with plant id get

                Toast.makeText(getApplicationContext(), "Clicked plant id= " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    }

