package com.project.gta.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class PlantMenu extends AppCompatActivity {
    public ListView LSTplants;
    private List<String> ListOfPlants = new ArrayList<String>();
    private PlantListAdapter PlantListAdapter;
    /*    ListView list;
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
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_menu);
        LSTplants = (ListView)findViewById(R.id.LSTplants);
        PlantListAdapter = new PlantListAdapter(this,0);
        LSTplants.setAdapter(PlantListAdapter);
        PlantListAdapter.notifyDataSetInvalidated();
/*
        PlantList adapter = new
                PlantList(PlantMenu.this, web, imageId);
        list=(ListView)findViewById(R.id.LSTplants);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(PlantMenu.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }*/
    }

    class PlantListAdapter extends ArrayAdapter<String> {

        public PlantListAdapter(Context context, int resource){
            super(context,resource);
        }
        @Override
        public int getCount(){
            return ListOfPlants.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.plant_element,null);

            }
            ListOfPlants.add("Bitch");
            //Textsplitter, zerteilt Strings bei , in Strings
            TextUtils.SimpleStringSplitter sss =
                    new TextUtils.SimpleStringSplitter(',');
            sss.setString(ListOfPlants.get(position));
            TextView tvPlant = (TextView) convertView.findViewById(R.id.tvPlantName);
            tvPlant.setText(sss.next());
            ImageView imgPlant = (ImageView) convertView.findViewById(R.id.imgPlant);
            try{
             //   imgPlant.setImageDrawable();
            }catch (Exception e)
            {
                Toast toast_notifications_enabled = Toast.makeText
                        (getApplicationContext(), "No file found", Toast.LENGTH_LONG);
                toast_notifications_enabled.show();
            }

            return convertView;
        }
    }

}