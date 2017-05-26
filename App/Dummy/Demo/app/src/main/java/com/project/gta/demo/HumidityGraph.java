package com.project.gta.demo;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HumidityGraph extends AppCompatActivity{

    private GraphViewManager graphViewManager = GraphViewManager.getInstance();
    private FileManager fileManager = FileManager.getInstance();
    private GraphView graph;
    private TextView txtView;

    public TextView getTxtView() {
        return txtView;
    }

    public void refreshGraph(){

        SimpleDateFormat formathours = new SimpleDateFormat("HH:mm");
        DataPoint[] points = graphViewManager.setDataPoints();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(series.getHighestValueY());

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(),formathours));
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

        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        fileManager.readData(getFilesDir());
        refreshGraph();
    }

}
