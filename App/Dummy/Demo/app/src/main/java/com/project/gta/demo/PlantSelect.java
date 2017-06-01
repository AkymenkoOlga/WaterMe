package com.project.gta.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlantSelect extends AppCompatActivity implements View.OnClickListener{

    private String PlantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_select);

        TextView plant1 = (TextView) findViewById(R.id.tvPlant1);
        plant1.setText(readDataPlant1());

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.FABplantEdit);
        fabEdit.setOnClickListener(this);
        FloatingActionButton fabCheck = (FloatingActionButton) findViewById(R.id.FABedit_plant_check);
        fabCheck.setOnClickListener(this);

        LinearLayout editName = (LinearLayout) findViewById(R.id.EditName);
        editName.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPlant1:
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.FABplantEdit:
                LinearLayout editName = (LinearLayout) findViewById(R.id.EditName);
                editName.setVisibility(View.VISIBLE);

                FloatingActionButton fabEditHide = (FloatingActionButton) findViewById(R.id.FABplantEdit);
                fabEditHide.setVisibility(View.INVISIBLE);
                break;
            case R.id.FABedit_plant_check:
                writeDataPlant1();
                readDataPlant1();
                LinearLayout checkName = (LinearLayout) findViewById(R.id.EditName);
                checkName.setVisibility(View.GONE);

                TextView plant1 = (TextView) findViewById(R.id.tvPlant1);
                plant1.setText(readDataPlant1());

                FloatingActionButton fabEditShow = (FloatingActionButton) findViewById(R.id.FABplantEdit);
                fabEditShow.setVisibility(View.VISIBLE);
            default:
                //Nothing
        }

    }

    public void writeDataPlant1() {
        SharedPreferences pref = getSharedPreferences("Plant1", 0);
        SharedPreferences.Editor editor = pref.edit();
        EditText textPlant1 = (EditText) findViewById(R.id.editText);
        PlantName = textPlant1.getText().toString().trim();
        if (!PlantName.equals("")) {
            editor.putString("Plant1Name", PlantName);
            editor.apply();
        }
    }

    private String readDataPlant1() {
        SharedPreferences pref = getSharedPreferences("Plant1", 0);
        return pref.getString("Plant1Name", "MyPlant"); //2.Param = Default, falls k
    }


}
