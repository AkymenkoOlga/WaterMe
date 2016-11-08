package com.project.gta.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }
    public void goto_settings(View view){
        setContentView(R.layout.activity_settings);
    }

/*   public void button_name(View view){    insert button_name
        setContentView(R.layout.activity_name);  insert activity_name
    }*/

}
