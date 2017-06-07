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
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlantSelect extends AppCompatActivity implements View.OnClickListener{


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String PlantName;
    private static int LOAD_IMAGE_RESULTS = 1;
    private String imgpath;

    private String alertText = "";
    private String alertImage = "";
    public int PlantID;

    private ImageView image;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabCheck;
    private TextView tvplant1;

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

        tvplant1 = (TextView) findViewById(R.id.tvPlant1);
        tvplant1.setText(readDataPlant1("text"));

        fabEdit = (FloatingActionButton) findViewById(R.id.FABplantEdit);
        fabEdit.setOnClickListener(this);
        fabCheck = (FloatingActionButton) findViewById(R.id.FABedit_plant_check);
        fabCheck.setOnClickListener(this);


        //Init Layouts
        LinearLayout editName = (LinearLayout) findViewById(R.id.EditName);
        editName.setVisibility(View.GONE);
        layout1 = (LinearLayout) findViewById(R.id.LayoutPlants);
        layout2 = (LinearLayout) findViewById(R.id.LayoutPlants2);
        layout3 = (LinearLayout) findViewById(R.id.LayoutPlants3);
        layout4 = (LinearLayout) findViewById(R.id.LayoutPlants4);


        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);

        verifyStoragePermissions(this);

        if(readDataPlant1("image") != ""){
            image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(readDataPlant1("image")),350,350,false));
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
            alertImage = imagePath;

            cursor.close();

            //image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath),350,350,false));
            //image.setImageURI(pickedImage)
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPlant1:
                startActivity(new Intent(this,SinglePlantMenu.class));
                break;
            /*case R.id.FABplantEdit:
                LinearLayout editName = (LinearLayout) findViewById(R.id.EditName);
                editName.setVisibility(View.VISIBLE);

                FloatingActionButton fabEditHide = (FloatingActionButton) findViewById(R.id.FABplantEdit);
                fabEditHide.setVisibility(View.INVISIBLE);
                break;
            case R.id.FABedit_plant_check:
                writeDataPlant1();
                readDataPlant1("text");
                LinearLayout checkName = (LinearLayout) findViewById(R.id.EditName);
                checkName.setVisibility(View.GONE);

                tvplant1.setText(readDataPlant1("text"));

                fabEdit.setVisibility(View.VISIBLE);*/
            case R.id. image:
                callGallery();
            default:
                //Nothing
        }

    }

    public void callGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
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

    //Plant1
    public void writeImagePlant1(String img){
        SharedPreferences pref = getSharedPreferences("Plant1", 0);
        SharedPreferences.Editor editor = pref.edit();
        imgpath = img;
        if (!imgpath.equals("")) {
            editor.putString("Plant1Image", imgpath);
            editor.apply();
        }
    }

    public void writeImagePlant(Plant plant){
        SharedPreferences pref = getSharedPreferences("Plant", 0);
        SharedPreferences.Editor editor = pref.edit();

        if (plant != null) {
            editor.putString("Plant1Image", imgpath);
            editor.apply();
        }
    }

    private String readDataPlant1(String string) {
        if(string.equals("text")) {
            SharedPreferences pref = getSharedPreferences("Plant1", 0);
            return pref.getString("Plant1Name", "MyPlant"); //2.Param = Default, falls k
        }
        if (string.equals("image"))
        {
            SharedPreferences pref = getSharedPreferences("Plant1",0);
            return pref.getString("Plant1Image", "");
        }
        return "";
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

    public void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertText = input.getText().toString();
                callGallery();
                if (plant1 == null){
                    plant1 = new Plant(alertText, alertImage);
                }
                else if (plant2==null){
                    plant2 = new Plant(alertText, alertImage);
                }
                else if (plant3==null){
                    plant3 = new Plant(alertText, alertImage);
                }
                else if (plant4==null){
                    plant4 = new Plant(alertText, alertImage);
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

}
