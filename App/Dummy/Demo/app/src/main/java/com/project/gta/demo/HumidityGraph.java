package com.project.gta.demo;
import java.text.SimpleDateFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HumidityGraph extends AppCompatActivity{

    private GraphViewManager graphViewManager = GraphViewManager.getInstance();
    private FileManager fileManager = FileManager.getInstance();
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private SimpleDateFormat formathours;

    public void refreshGraph(){

        fileManager.readData(getFilesDir());
        DataPoint[] points = graphViewManager.setDataPoints();
        series = new LineGraphSeries<>(points);
        series.setDrawDataPoints(true);
        series.setColor(0xFF02a721);
        graph.addSeries(series);

        graph.getViewport().setMinY(0);
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setNumVerticalLabels(5);

        graph.getGridLabelRenderer().setNumVerticalLabels(7);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(),formathours));
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
        formathours = new SimpleDateFormat("HH:mm");

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
                        formathours = new SimpleDateFormat("HH:mm");
                        refreshGraph();
                        break;
                    case(R.id.Radio1week):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*7);
                        formathours = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(R.id.Radio3days):
                        graph.getViewport().setMinX(System.currentTimeMillis()-86400000*3);
                        formathours = new SimpleDateFormat("dd-MM");
                        refreshGraph();
                        break;
                    case(R.id.RadioOptimal):
                        formathours = new SimpleDateFormat("dd-MM");
                        graph.getViewport().setMaxX(series.getHighestValueX());
                        graph.getViewport().setMinX(series.getLowestValueX());
                        refreshGraph();
                        break;
                }
            }
        });


    }

}
