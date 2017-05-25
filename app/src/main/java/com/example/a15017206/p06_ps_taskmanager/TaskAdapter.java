package com.example.a15017206.p06_ps_taskmanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017206 on 25/05/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    private  ArrayList<Task> someArrayList;
    private Context c;
    private TextView tvID, tvName, tvDesc, tvRemindInSeconds;

    public TaskAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Task> objects) {
        super(context, resource, objects);
        someArrayList = objects;
        this.c = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //connect to row.xml
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tvID = (TextView) rowView.findViewById(R.id.tvID);
        tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);
        tvRemindInSeconds = (TextView) rowView.findViewById(R.id.tvRemindInSeconds);


        Task currentTask = someArrayList.get(position);

        //Set the tv to show the values
        tvID.setText(currentTask.getId()+"");
        tvName.setText(currentTask.getName());
        tvDesc.setText(currentTask.getDescription());
        tvRemindInSeconds.setText(currentTask.getRemindInSeconds()+"");

        return rowView;
    }
}
