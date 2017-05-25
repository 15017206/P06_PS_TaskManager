package com.example.a15017206.p06_ps_taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    int reqCode = 12345;

    Button btnAddTask;
    EditText etName, etDesc, etSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etName = (EditText) findViewById(R.id.etName);
        etSeconds = (EditText) findViewById(R.id.etNumberSeconds);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int remindInSeconds = Integer.parseInt(etSeconds.getText().toString());

                DBHelper dbh = new DBHelper(AddActivity.this);
                long row_affected = dbh.insertTask(name, desc, remindInSeconds);
                dbh.close();

                if (row_affected != -1) {
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent();
                setResult(RESULT_OK, i);

                //launch notification here
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.parseInt(etSeconds.getText().toString()));

                Intent intent = new Intent(AddActivity.this,
                        MyReceiver.class);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("desc", etDesc.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                finish();
            }

        });
    }
}
