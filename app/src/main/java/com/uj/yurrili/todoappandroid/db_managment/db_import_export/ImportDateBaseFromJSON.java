package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.JsonReader;
import android.util.Log;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.db_managment.Entries;
import com.uj.yurrili.todoappandroid.objects.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public class ImportDateBaseFromJSON implements ImportExport {

    public List<Task> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Task> list = readTasksArray(reader);
        reader.close();
        return list;
    }

    public List<Task> readTasksArray(JsonReader reader) throws IOException {
        List<Task> messages  = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readTask(reader));
        }
        reader.endArray();
        return messages;
    }

    public Task readTask(JsonReader reader) throws IOException {
        Task newTask = new Task();

        reader.beginObject();
        while (reader.hasNext()) {

            String temp;
            String name = reader.nextName();
            if (name.equals(Entries.Task._ID)) {
                newTask.setId(Integer.parseInt(reader.nextString()));
            } else if (name.equals(Entries.Task.COLUMN_TITLE)) {
                temp = reader.nextString();
                Log.d("IMPORT", temp);
                newTask.setTitle(temp);
            } else if (name.equals(Entries.Task.COLUMN_DESCRIPTION)) {
                temp = reader.nextString();
                if (!temp.equals(""))
                    newTask.setDescription(temp);
            } else if (name.equals(Entries.Task.COLUMN_URL_TO_ICON)) {
                temp = reader.nextString();
                if (!temp.equals(""))
                    newTask.setUrl_to_icon(temp);
            } else if (name.equals(Entries.Task.COLUMN_TIME_END)) {
                temp = reader.nextString();
                if (!temp.equals(""))
                    newTask.setTime_end(Long.parseLong(temp));
            } else if (name.equals(Entries.Task.COLUMN_TIMESTAMP)) {
                newTask.setCreate_at(new Timestamp(Long.parseLong(reader.nextString())));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return newTask;
    }

    @Override
    public boolean moveDataBase(Context mContext) {
        DataBaseHelper dba = new DataBaseHelperImpl(mContext);

        List<Task> tasks = null;
        try {
            tasks = readJsonStream(new FileInputStream(new File(android.os.Environment.getExternalStorageDirectory(), pathToFile)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        dba.setTasksFromJson(tasks);
        Log.d("IMPORT", "SUCCEDED");
        return true;

    }
}

