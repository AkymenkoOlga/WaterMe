package com.project.gta.demo;

import android.content.Intent;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class HumidityGraph extends AppCompatActivity{

    private TextView txtView;

    public TextView getTxtView() {
        return txtView;
    }

    private GraphView graph;

    public GraphView getGraph() {
        return graph;
    }

    private DataPoint[] points;
    private LineGraphSeries<DataPoint> series;
    private Date maxDate;

    public void createDataPointArray(int ArrSize){
       points = new DataPoint[ArrSize];
    }
    public void setDataPoints(String date,int val, int index){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date_ = format.parse(date);
            points[index] = new DataPoint(date_,val);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void refreshGraph(){
        series = new LineGraphSeries<>(points);
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setNumVerticalLabels(5);
        //graph.getGridLabelRenderer().setHumanRounding(false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_diagram);
        graph = (GraphView) findViewById(R.id.graph);
        Button refreshB = (Button) findViewById(R.id.BTNrefresh);
        refreshB.setOnClickListener(BluetoothAdministration.getInstance(this));

        txtView = (TextView)findViewById(R.id.txtfile);
        txtView.setMovementMethod(new ScrollingMovementMethod());

        //graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        //graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


    }

}
