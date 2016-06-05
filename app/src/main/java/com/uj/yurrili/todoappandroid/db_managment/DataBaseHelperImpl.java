package com.uj.yurrili.todoappandroid.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uj.yurrili.todoappandroid.Utilities;
import com.uj.yurrili.todoappandroid.objects.Task;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDateTime;

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

    public DataBaseHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        JodaTimeAndroid.init(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entries.getSQL_CREATE_ENTRIES_Task());
        Log.d("DB", "created table Task");

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, "Paid for food");
        db.insert(Entries.Task.TABLE_NAME, null, values);
        values.put(Entries.Task.COLUMN_URL_TO_ICON, "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/11.10.png");

        values.put(Entries.Task.COLUMN_TITLE, "Paid for gym");
        db.insert(Entries.Task.TABLE_NAME, null, values);

        values.put(Entries.Task.COLUMN_TITLE, "App for GameDesire");
        values.put(Entries.Task.COLUMN_TIME_END, Utilities.jodaToSQLTimestamp(LocalDateTime.now()).getTime());
        values.put(Entries.Task.COLUMN_URL_TO_ICON, "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/6.10.png");
        db.insert(Entries.Task.TABLE_NAME, null, values);

        values.put(Entries.Task.COLUMN_TITLE, "App for STH");
        values.put(Entries.Task.COLUMN_TIME_END, Utilities.jodaToSQLTimestamp(LocalDateTime.now()).getTime());
        values.put(Entries.Task.COLUMN_URL_TO_ICON, "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/9.10.png");
        db.insert(Entries.Task.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "UPGRADE");
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Task.TABLE_NAME);
    }

    @Override
    public void insertTask(Task task) {
        Log.d("DB", "insert Task" + task.getTitle());
        SQLiteDatabase dba = this.getWritableDatabase();
        Long timestamp = null;

        if (task.getTime_end() != null) {
            timestamp = task.getTime_end().getTime();
        }

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, task.getTitle());
        values.put(Entries.Task.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Entries.Task.COLUMN_TIME_END, timestamp);
        values.put(Entries.Task.COLUMN_TIMESTAMP, Utilities.jodaToSQLTimestamp(LocalDateTime.now()).getTime());
        values.put(Entries.Task.COLUMN_URL_TO_ICON, task.getUrl_to_icon());

        dba.insert(Entries.Task.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void removeTask(Task task) {
        Log.d("DB", "insert Task" + task.getTitle());
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.Task.TABLE_NAME, Entries.Task._ID + "='" + task.getId() + "'", null);
        dba.close();
    }


    @Override
    public void updateTask(Task task) {
        Log.d("DB", "insert Task" + task.getTitle());
        SQLiteDatabase dba = this.getWritableDatabase();
        Long timestamp = null;

        if (task.getTime_end() != null) {
            timestamp = task.getTime_end().getTime();
        }

        ContentValues values = new ContentValues();

        values.put(Entries.Task.COLUMN_TITLE, task.getTitle());
        values.put(Entries.Task.COLUMN_DESCRIPTION, task.getDescription());
        values.put(Entries.Task.COLUMN_TIME_END, timestamp);
        values.put(Entries.Task.COLUMN_URL_TO_ICON, task.getUrl_to_icon());

        dba.update(Entries.Task.TABLE_NAME, values, Entries.Task._ID + "='" + task.getId() + "'", null);
        dba.close();
    }

    @Override
    public void setTasksFromJson(List<Task> list) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.execSQL("DROP TABLE IF EXISTS " + Entries.Task.TABLE_NAME);
        dba.execSQL(Entries.getSQL_CREATE_ENTRIES_Task());
        Log.d("DB", "insert Tasks from JSON");
        for (Task task : list) {
            Long timestamp = null;

            if (task.getTime_end() != null) {
                timestamp = task.getTime_end().getTime();
            }

            ContentValues values = new ContentValues();
            values.put(Entries.Task._ID, task.getId());
            values.put(Entries.Task.COLUMN_TITLE, task.getTitle());
            values.put(Entries.Task.COLUMN_DESCRIPTION, task.getDescription());
            values.put(Entries.Task.COLUMN_TIME_END, timestamp);
            values.put(Entries.Task.COLUMN_TIMESTAMP, Utilities.jodaToSQLTimestamp(LocalDateTime.now()).getTime());
            values.put(Entries.Task.COLUMN_URL_TO_ICON, task.getUrl_to_icon());

            dba.insert(Entries.Task.TABLE_NAME, null, values);

        }
        dba.close();
    }

    @Override
    public Task getTask(String id) throws MalformedURLException {
        Log.d("DB", "get Task" + id);
        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor c = dba.query(Entries.Task.TABLE_NAME,
                Entries.selectAllTasks,
                Entries.Task._ID + "='" + id + "'", null,
                null, null, null);

        if (c != null) {
            c.moveToFirst();
            Task a = new Task();
            a.setId(c.getInt(0));
            a.setTitle(c.getString(1));
            a.setDescription(c.getString(2));
            a.setUrl_to_icon(c.getString(3));
            a.setTime_end(c.getLong(4));
            a.setCreate_at(new Timestamp(c.getLong(5)));
            c.close();
            return a;

        }
        dba.close();
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
                Task a = new Task();
                a.setId(c.getInt(0));
                a.setTitle(c.getString(1));
                a.setDescription(c.getString(2));
                a.setUrl_to_icon(c.getString(3));
                a.setTime_end(c.getLong(4));
                a.setCreate_at(new Timestamp(c.getLong(5)));
                list.add(a);
            }
            c.close();
        }
        dba.close();
        return list;
    }


}
