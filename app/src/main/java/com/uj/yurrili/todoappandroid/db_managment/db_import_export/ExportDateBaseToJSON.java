package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.db_managment.Entries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;


/**
 * Created by Yuri on 2016-06-04.
 */
public class ExportDateBaseToJSON implements ImportExport {
    @Override
    public void moveDataBase(Context mContext) throws MalformedURLException {

        String myPath = mContext.getDatabasePath(DataBaseHelperImpl.DATABASE_NAME).getPath();
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor c = myDataBase.query(Entries.Task.TABLE_NAME, Entries.selectAllTasks, null, null, null, null, null);
        JSONObject json = new JSONObject();
        JSONArray resultSet = new JSONArray();

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                int totalColumn = c.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    Log.d("TAG_NAME_ILE ::::::", i+"");
                    if (c.getColumnName(i) != null) {
                        try {
                            if (c.getString(i) != null) {
                                Log.d("TAG_NAME", c.getString(i));
                                rowObject.put(c.getColumnName(i), c.getString(i));
                            } else {
                                Log.d("TAG_NAME", c.getColumnName(i));
                                rowObject.put(c.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
            }
            c.close();
        }

        try {
            json.put("Task", resultSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        create(json.toString());

    }

    public void create(String result) {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(new File(android.os.Environment.getExternalStorageDirectory(), pathToFile), true);
            fos.write(result.getBytes());
            fos.close();
            Log.d("TAG_NAME", android.os.Environment.getExternalStorageDirectory().getPath());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
