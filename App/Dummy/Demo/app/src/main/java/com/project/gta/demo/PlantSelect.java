package com.project.gta.demo;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class PlantSelect extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static int LOAD_IMAGE_RESULTS = 1;

    private String plantName = "";
    private String plantImage = "";

    public static int id;
    //plants
    public Plant plant1;
    public Plant plant2;
    public Plant plant3;
    public Plant plant4;
    //Layouts
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    private TextView tvplant1;
    private TextView tvplant2;
    private TextView tvplant3;
    private TextView tvplant4;
    public static SharedPreferences  mPrefs;

    //Actionbar
    @Override
    protected void onStart() {
        super.onStart();
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_select);
        mPrefs = getPreferences(MODE_PRIVATE);

        ImageButton del1 = (ImageButton) findViewById(R.id.btnDel1);
        del1.setOnClickListener(this);
        ImageButton del2 = (ImageButton) findViewById(R.id.btnDel2);
        del2.setOnClickListener(this);
        ImageButton del3 = (ImageButton) findViewById(R.id.btnDel3);
        del3.setOnClickListener(this);
        ImageButton del4 = (ImageButton) findViewById(R.id.btnDel4);
        del4.setOnClickListener(this);


        //Init Layout
        layout1 = (LinearLayout) findViewById(R.id.LayoutPlants1);
        layout2 = (LinearLayout) findViewById(R.id.LayoutPlants2);
        layout3 = (LinearLayout) findViewById(R.id.LayoutPlants3);
        layout4 = (LinearLayout) findViewById(R.id.LayoutPlants4);

        tvplant1 = (TextView) findViewById(R.id.tvPlant1);
        tvplant2 = (TextView) findViewById(R.id.tvPlant2);
        tvplant3 = (TextView) findViewById(R.id.tvPlant3);
        tvplant4 = (TextView) findViewById(R.id.tvPlant4);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);

        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);

        tvplant1.setOnClickListener(this);
        tvplant2.setOnClickListener(this);
        tvplant3.setOnClickListener(this);
        tvplant4.setOnClickListener(this);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);

        retrieve();
        verifyStoragePermissions(this);

    }

    private void retrieve(){
        Gson gson = new Gson();
        String json = mPrefs.getString("plant1", "");
        plant1 = gson.fromJson(json, Plant.class);
        if (plant1 != null) {
            layout1.setVisibility(View.VISIBLE);
            tvplant1.setText(plant1.name);
            setImage(plant1.plantImagePath, image1);
        }
        json = mPrefs.getString("plant2", "");
        plant2 = gson.fromJson(json, Plant.class);
        if (plant2 != null) {
            layout2.setVisibility(View.VISIBLE);
            tvplant2.setText(plant2.name);
            setImage(plant2.plantImagePath, image2);
        }
        json = mPrefs.getString("plant3", "");
        plant3 = gson.fromJson(json, Plant.class);
        if (plant3 != null) {
            layout3.setVisibility(View.VISIBLE);
            tvplant3.setText(plant3.name);
            setImage(plant3.plantImagePath, image3);
        }
        json = mPrefs.getString("plant4", "");
        plant4 = gson.fromJson(json, Plant.class);
        if (plant4 != null) {
            layout4.setVisibility(View.VISIBLE);
            tvplant4.setText(plant4.name);
            setImage(plant4.plantImagePath, image4);
        }
    }
    //Storage Permission for Android API23+
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();

            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            //writeImagePlant1(imagePath);
            plantImage = imagePath;

            cursor.close();

            //image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath),350,350,false));
            //image.setImageURI(pickedImage)
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPlant1:
                id = 1;
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.tvPlant2:
                id = 2;
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.tvPlant3:
                id = 3;
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.tvPlant4:
                id = 4;
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            case R.id.image1:
                id = 1;
                startActivity(new Intent(this, SinglePlantMenu.class));
                break;
            case R.id.image2:
                id = 2;
                startActivity(new Intent(this, SinglePlantMenu.class));
                break;
            case R.id.image3:
                id = 3;
                startActivity(new Intent(this, SinglePlantMenu.class));
                break;
            case R.id.image4:
                id = 4;
                startActivity(new Intent(this, SinglePlantMenu.class));
                break;
            case R.id.btnDel1:
                delete(plant1,"plant1");
                layout1.setVisibility(View.GONE);
                break;
            case R.id.btnDel2:
                delete(plant2,"plant2");
                layout2.setVisibility(View.GONE);
                break;
            case R.id.btnDel3:
                delete(plant3,"plant3");
                layout3.setVisibility(View.GONE);
                break;
            case R.id.btnDel4:
                delete(plant4,"plant4");
                layout4.setVisibility(View.GONE);
                break;
            default:
                //Nothing
        }
    }

    public void callGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    //Actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_plant_select, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(android.R.id.home == item.getItemId()){
            Intent intent = new Intent(this, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else
        {
            //if press '+'
            dialog();
        }
        return true;
    }

    private void save(Plant plant, String name){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(plant);
        prefsEditor.putString(name, json);
        prefsEditor.commit();
    }

    private void delete(Plant plant ,String name){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(name, null);
        prefsEditor.apply();
        plant = null;
    }
    public void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Give your plant a name:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        callGallery();
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                plantName = input.getText().toString();


                if (plant1 == null){
                    plant1 = new Plant(plantName, plantImage);
                    setImage(plant1.plantImagePath, image1);
                    tvplant1.setText(plant1.name);
                    layout1.setVisibility(View.VISIBLE);
                    save(plant1, "plant1");

                }
                else if (plant2==null){
                    plant2 = new Plant(plantName, plantImage);
                    setImage(plant2.plantImagePath, image2);
                    tvplant2.setText(plant2.name);
                    layout2.setVisibility(View.VISIBLE);
                    save(plant2,"plant2");
                }
                else if (plant3==null){
                    plant3 = new Plant(plantName, plantImage);
                    setImage(plant3.plantImagePath, image3);
                    tvplant3.setText(plant3.name);
                    layout3.setVisibility(View.VISIBLE);
                    save(plant3,"plant3");
                }
                else if (plant4==null){
                    plant4 = new Plant(plantName, plantImage);
                    setImage(plant4.plantImagePath, image4);
                    tvplant4.setText(plant4.name);
                    layout4.setVisibility(View.VISIBLE);
                    save(plant4, "plant4");
                }
                else{
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "No more plants possible", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setImage(String path, ImageView image){
        image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path),350,350,false));
    }

}
