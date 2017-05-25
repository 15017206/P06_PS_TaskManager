package com.example.a15017206.p06_ps_taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15017206 on 25/05/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_NAME = "note";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_REMIND_IN_SECONDS = "remind_in_secs";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_REMIND_IN_SECONDS + " INTEGER ) ";
        db.execSQL(createNoteTableSql);
        Log.e("info", "created tables");
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertTask(String name, String desc, int seconds) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);

        String seconds2 = String.valueOf(seconds);

        if (seconds2 != "") {
            values.put(COLUMN_REMIND_IN_SECONDS, seconds);
        }

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.e("DBHelper", "Insert failed");
        }
        db.close();
        Log.e("SQL Insert", "" + result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + " ," + COLUMN_NAME + " ," + COLUMN_DESC + " ," + COLUMN_REMIND_IN_SECONDS + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int remindInSeconds = Integer.parseInt(cursor.getString(3));
                //create a task object, obj
                Task obj = new Task(id, name, desc, remindInSeconds);

                //add to arraylist
                tasks.add(obj);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
