package com.project.gta.demo;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;


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


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case (R.id.Radio24h):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000);
                        format = new SimpleDateFormat("HH:mm");
                        refreshGraph();
                        break;
                    case(R.id.Radio1week):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*7);
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(R.id.Radio3days):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*3);
                        format = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(R.id.RadioOptimal):
                        format = new SimpleDateFormat("HH:mm");
                        graph.getViewport().setMaxX(series.getHighestValueX());
                        graph.getViewport().setMinX(series.getLowestValueX());
                        refreshGraph();
                        break;
                }
            }
        });
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

                } else {
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

