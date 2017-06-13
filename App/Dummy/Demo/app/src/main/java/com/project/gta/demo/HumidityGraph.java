package com.project.gta.demo;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;



import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HumidityGraph extends AppCompatActivity{

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    public int numberOfValues = 0;
    private SimpleDateFormat format;
    public Queue<String> date = new LinkedList<>();
    public Queue<Integer> val = new LinkedList<>();
    public String plantID;

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_diagram);
        graph = (GraphView) findViewById(R.id.graph);
        final Button refreshB = (Button) findViewById(R.id.BTNrefresh);
        refreshB.setOnClickListener(BluetoothAdministration.getInstance(this));
        format = new SimpleDateFormat("HH:mm");
        plantID = String.valueOf(PlantSelect.id);
        refreshGraph();
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_graph, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(android.R.id.home == item.getItemId()){
            Intent intent = new Intent(this, SinglePlantMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            if (item.getTitle().equals("Change scale"))
                dialog();
            if (item.getTitle().equals("Delete data"))

            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                delete();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete all humidity data of the current plant?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }
        return true;
    }

    void delete()
    {
        BluetoothAdministration.getInstance(this).execute("delete" + plantID);
    }
    void dialog(){
        final String[] grpname = {"24h","three days", "one week", "optimal"};
        AlertDialog.Builder altBld = new AlertDialog.Builder(this);
        altBld.setTitle("Choose scale");
        altBld.setSingleChoiceItems(grpname, -1, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000);
                        graph.getViewport().setMaxX(System.currentTimeMillis());
                        format = new SimpleDateFormat("HH:mm");
                        refreshGraph();
                        break;
                    case 1:
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*3);
                        graph.getViewport().setMaxX(System.currentTimeMillis());
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case 2:
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*7);
                        graph.getViewport().setMaxX(System.currentTimeMillis());
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case 3:
                        format = new SimpleDateFormat("HH:mm");
                        graph.getViewport().setMaxX(series.getHighestValueX());
                        graph.getViewport().setMinX(series.getLowestValueX());
                        refreshGraph();
                        break;
                }
                dialog.dismiss();// dismiss the alertbox after chose option
            }
        });
        AlertDialog alert = altBld.create();
        alert.show();
    }

    public void readData() {

        numberOfValues = 0;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput("Val" + plantID + ".txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String s;

            while ((s = br.readLine()) != null) {
                if (Character.isDigit(s.charAt(0))) {
                    if (sb.length() > 0) {
                        sb.append('\n');
                    }
                }
                sb.append(s);
                extractData(s);
            }
            fis.close();
            isr.close();
            br.close();
        } catch (IOException t) {
            Log.e("", "load()", t);
        }
    }

    public void refreshGraph(){
        readData();
        DataPoint[] points = setDataPoints();
        series = new LineGraphSeries<>(points);
        series.setDrawDataPoints(true);
        series.setColor(0xFF02a721);
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setNumVerticalLabels(6);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Humidity in %");

        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(),format));
        //graph.getGridLabelRenderer().setHumanRounding(false);


    }
    public void extractData(String line) {
        date.add(line.substring(0, 20));
        val.add(Integer.parseInt(line.substring(21, 25)));
        numberOfValues++;
    }

    public DataPoint[] setDataPoints() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        DataPoint[] points = new DataPoint[numberOfValues];
        try {
            for(int index = 0; index < numberOfValues; index++) {

                Date date1 = sdf.parse(date.remove());
                points[index] = new DataPoint(date1, val.remove());
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return points;
    }
}

