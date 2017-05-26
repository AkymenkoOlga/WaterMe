package com.project.gta.demo;

import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Paul on 26.05.2017.
 */

public class GraphViewManager {

    //region Singleton
    public static GraphViewManager getInstance() {
        if (_instance == null)
            _instance = new GraphViewManager();
        return _instance;
    }

    private GraphViewManager() {
    }

    private static GraphViewManager _instance = null;
    //endregion

    public Queue<String> date = new LinkedList<String>();
    public Queue<Integer> val = new LinkedList<Integer>();

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public int NumerOfValues = 0;

    void extractData(String line) {
        date.add(line.substring(0, 20));
        val.add(Integer.parseInt(line.substring(21, 25)));
        NumerOfValues++;
    }

    public DataPoint[] setDataPointsHours() {
        DataPoint[] points = new DataPoint[NumerOfValues];
        try {
            for(int index = 0; index < NumerOfValues; index++) {

                Date date_ = format.parse(date.remove());
                points[index] = new DataPoint(date_, val.remove());
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return points;
    }
}
