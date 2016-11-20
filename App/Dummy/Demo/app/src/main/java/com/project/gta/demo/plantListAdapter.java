package com.project.gta.demo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.FieldPosition;
import java.util.List;

/**
 * Created by TInf on 19.11.2016.
 */

public class plantListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Plant> mPlantList;

    // Constructor


    public plantListAdapter(Context mContext, List<Plant> mPlantList) {
        this.mContext = mContext;
        this.mPlantList = mPlantList;
    }

    @Override
    public int getCount() {
        return mPlantList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =View.inflate(mContext, R.layout.plant_overview, null);
        TextView plName = (TextView)v.findViewById(R.id.pl_name);
        //set Text for TextView
        plName.setText(mPlantList.get(position).getName());
        //save plant id to tag
        v.setTag(mPlantList.get(position).getId());

        return v;
    }




}
