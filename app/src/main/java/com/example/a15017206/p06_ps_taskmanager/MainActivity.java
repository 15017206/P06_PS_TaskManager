package com.example.a15017206.p06_ps_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button btnAdd;
    ArrayList<Task> al;
    ArrayAdapter aa;

    @Override
    protected void onResume() {
        super.onResume();
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        al = new ArrayList<Task>();

        al.add(new Task(10, "some name", "some desc", 3));

        btnAdd = (Button) findViewById(R.id.btnAddTask);

        aa = new TaskAdapter(this, R.layout.activity_main, al);


        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(dbh.getAllTasks());
        dbh.close();

        lv.setAdapter(aa);
        aa.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
         DBHelper dbh = new DBHelper(MainActivity.this);
            aa = new TaskAdapter(this, R.layout.activity_main, al);
            al.clear();
            al.addAll(dbh.getAllTasks());
            dbh.close();

            lv.setAdapter(aa);
            aa.notifyDataSetChanged();
        }
    }



}
