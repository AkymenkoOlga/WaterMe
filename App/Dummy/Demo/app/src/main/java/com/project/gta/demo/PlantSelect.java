package com.project.gta.demo;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlantSelect extends AppCompatActivity implements View.OnClickListener{


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String PlantName;
    private static int LOAD_IMAGE_RESULTS = 1;
    private String imgpath;

    private ImageView image;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabCheck;
    private TextView plant1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_select);

        plant1 = (TextView) findViewById(R.id.tvPlant1);
        plant1.setText(readDataPlant1("text"));

        fabEdit = (FloatingActionButton) findViewById(R.id.FABplantEdit);
        fabEdit.setOnClickListener(this);
        fabCheck = (FloatingActionButton) findViewById(R.id.FABedit_plant_check);
        fabCheck.setOnClickListener(this);

        LinearLayout editName = (LinearLayout) findViewById(R.id.EditName);
        editName.setVisibility(View.GONE);

        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);

        verifyStoragePermissions(this);

        if(readDataPlant1("image") != ""){
            image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(readDataPlant1("image")),200,200,false));
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
            writeImagePlant1(imagePath);

            cursor.close();

            image.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath),200,200,false));
            //image.setImageURI(pickedImage)
        }
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
                readDataPlant1("text");
                LinearLayout checkName = (LinearLayout) findViewById(R.id.EditName);
                checkName.setVisibility(View.GONE);

                plant1.setText(readDataPlant1("text"));

                fabEdit.setVisibility(View.VISIBLE);
            case R.id. image:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
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

    public void writeImagePlant1(String img){
        SharedPreferences pref = getSharedPreferences("Plant1", 0);
        SharedPreferences.Editor editor = pref.edit();
        imgpath = img;
        if (!imgpath.equals("")) {
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


}
