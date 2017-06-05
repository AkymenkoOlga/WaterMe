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
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HumidityGraph extends AppCompatActivity{

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private static final String FILENAME = "Values.txt";
    public int NumberOfValues = 0;
    private SimpleDateFormat format;
    public Queue<String> date = new LinkedList<String>();
    public Queue<Integer> val = new LinkedList<Integer>();

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
        //txtView.setText(fileManager.readData(getFilesDir()));
        format = new SimpleDateFormat("HH:mm");

        refreshGraph();
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setMinX(series.getLowestValueX());
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
        else
        {
            dialog();
        }
        return true;
    }

    void dialog(){
        final String[] grpname = {"24h","three days", "one week", "optimal"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("Choose scale");
        alt_bld.setSingleChoiceItems(grpname, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case (0):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000);
                        format = new SimpleDateFormat("HH:mm");
                        refreshGraph();
                        break;
                    case(1):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*3);
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(2):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*7);
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(3):
                        format = new SimpleDateFormat("HH:mm");
                        graph.getViewport().setMaxX(series.getHighestValueX());
                        graph.getViewport().setMinX(series.getLowestValueX());
                        refreshGraph();
                        break;
                }
                dialog.dismiss();// dismiss the alertbox after chose option

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public void readData() {

        NumberOfValues = 0;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput(FILENAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String s;
            // Datei zeilenweise lesen
            while ((s = br.readLine()) != null) {
                // ggf. Zeilenumbruch hinzufügen
                if (!Character.isDigit(s.charAt(0))) {

                }
                else {
                    if (sb.length() > 0) {
                        sb.append('\n');
                    }
                    sb.append(s);
                    extractData(s);

                }
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

        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setNumVerticalLabels(5);

        graph.getGridLabelRenderer().setNumVerticalLabels(7);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(),format));
        //graph.getGridLabelRenderer().setHumanRounding(false);

    }
    public void extractData(String line) {
        date.add(line.substring(0, 20));
        val.add(Integer.parseInt(line.substring(21, 25)));
        NumberOfValues++;
    }

    public DataPoint[] setDataPoints() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        DataPoint[] points = new DataPoint[NumberOfValues];
        try {
            for(int index = 0; index < NumberOfValues; index++) {

                Date date_ = sdf.parse(date.remove());
                points[index] = new DataPoint(date_, val.remove());
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return points;
    }
}

