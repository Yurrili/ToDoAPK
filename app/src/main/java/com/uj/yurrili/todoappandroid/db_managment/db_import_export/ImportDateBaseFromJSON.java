package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public class ImportDateBaseFromJSON implements ImportExport {

    public String loadJSONFromFile(Context mContext) {
        String json = null;
        try {
            FileInputStream notes_xml = new FileInputStream(new File(android.os.Environment.getExternalStorageDirectory(), pathToFile));
            byte fileContent[] = new byte[(int)notes_xml.available()];
                //The information will be content on the buffer.

            notes_xml.read(fileContent);

            json = new String(fileContent);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void moveDataBase(Context mContext) {
        DataBaseHelper dba = new DataBaseHelperImpl(mContext);

        String strJson = loadJSONFromFile(mContext);
        List<Task> tasks = new ArrayList<>();
        try {

           // JSONObject jsonRootObject = new JSONObject(strJson);
            JSONArray jsonArray = new JSONArray(strJson);
//            JSONArray jsonArray = new JSONArray(strJson);
            //Get the instance of JSONArray that contains JSONObjects
          //  JSONArray jsonArray = jsonRootObject.getJSONArray("Task");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Task newTask = new Task();
                newTask.setId(Integer.parseInt(jsonObject.optString(Entries.Task._ID)));
                newTask.setTitle(jsonObject.optString(Entries.Task.COLUMN_TITLE));
                String temp = jsonObject.optString(Entries.Task.COLUMN_DESCRIPTION);
                if( !temp.equals("")) {
                    newTask.setDescription(temp);
                }
                temp = jsonObject.optString(Entries.Task.COLUMN_URL_TO_ICON);
                if( !temp.equals("")) {
                    newTask.setUrl_to_icon(temp);
                }
                temp = jsonObject.optString(Entries.Task.COLUMN_TIME_END);
                if( !temp.equals("")){
                    newTask.setTime_end(Long.parseLong(temp));
                }

                newTask.setCreate_at(new Timestamp(jsonObject.optLong(Entries.Task.COLUMN_TIMESTAMP)));
                tasks.add(newTask);
            }

        } catch (JSONException e) {e.printStackTrace();}

        dba.setTasksFromJson(tasks);
    }
}

