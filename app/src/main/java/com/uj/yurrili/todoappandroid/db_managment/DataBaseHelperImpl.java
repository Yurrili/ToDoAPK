package com.uj.yurrili.todoappandroid.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 2016-06-01.
 */

public class DataBaseHelperImpl extends SQLiteOpenHelper implements DataBaseHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BudgetFlow.db";

    public DataBaseHelperImpl (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entries.getSQL_CREATE_ENTRIES_Task());
        Log.d("DB", "created table Task");

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, "Paid for food");
        db.insert(Entries.Task.TABLE_NAME, null, values);

        values.put(Entries.Task.COLUMN_TITLE, "Paid for gym");
        db.insert(Entries.Task.TABLE_NAME, null, values);

        values.put(Entries.Task.COLUMN_TITLE, "App for GameDesire");
        values.put(Entries.Task.COLUMN_TIME_END, "1464790674");
        values.put(Entries.Task.COLUMN_URL_TO_ICON, "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/6.10.png");
        db.insert(Entries.Task.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Task.TABLE_NAME);
    }

    @Override
    public void insertTask(Task task) {
        SQLiteDatabase dba = this.getWritableDatabase();

        Long timestamp = task.getTime_end().getTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, task.getTitle());
        values.put(Entries.Task.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Entries.Task.COLUMN_TIME_END, timestamp);
        values.put(Entries.Task.COLUMN_URL_TO_ICON, task.getUrl_to_icon().toString());

        dba.insert(Entries.Task.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void removeTask(Task task) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.Task.TABLE_NAME, Entries.Task._ID + "='" + task.getId() + "'", null);
        dba.close();
    }

    @Override
    public void updateTask(Task task) {
        SQLiteDatabase dba = this.getWritableDatabase();

        Long timestamp = task.getTime_end().getTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, task.getTitle());
        values.put(Entries.Task.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Entries.Task.COLUMN_TIME_END, timestamp);
        values.put(Entries.Task.COLUMN_URL_TO_ICON, task.getUrl_to_icon().toString());


        dba.update(Entries.Task.TABLE_NAME, values, Entries.Task._ID + "='" + task.getId() + "'", null);
        dba.close();
    }

    @Override
    public Task getTask(Integer id) throws MalformedURLException {
        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor c = dba.query(Entries.Task.TABLE_NAME,
                Entries.selectAllTasks,
                Entries.Task._ID+"=?",new String[]{id.toString()},
                null, null, null);

        if (c != null) {
            return new Task(  c.getInt(0), // ID
                    c.getString(1), // TITLE
                    c.getString(2), // DESCRIPTION
                    c.getString(3), // URL_TO_ICON
                    new Timestamp(c.getLong(4)) // TIMESTAMP
            );
        }
        return null;
    }

    @Override
    public List<Task> getTasks() throws MalformedURLException {
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Task> list = new ArrayList<>();

        Cursor c = dba.query(Entries.Task.TABLE_NAME,
                Entries.selectAllTasks,
                null, null, null, null, null);

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Task(  c.getInt(0), // ID
                                    c.getString(1), // TITLE
                                    c.getString(2), // DESCRIPTION
                                    c.getString(3), // URL_TO_ICON
                                    new Timestamp(c.getLong(4)) // TIMESTAMP
                       ));
            }
        }

        c.close();
        dba.close();
        return list;
    }
}
