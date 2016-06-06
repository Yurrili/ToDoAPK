package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.JsonWriter;
import android.util.Log;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.db_managment.Entries;
import com.uj.yurrili.todoappandroid.objects.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.List;


/**
 * Created by Yuri on 2016-06-04.
 */
public class ExportDateBaseToJSON implements ImportExport {

    public void writeJsonStream(OutputStream out, List<Task> messages) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeMessagesArray(writer, messages);
        writer.close();
    }


    public void writeMessagesArray(JsonWriter writer, List<Task> messages) throws IOException {
        writer.beginArray();
        for (Task message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }

    public void writeMessage(JsonWriter writer, Task message) throws IOException {
        Log.d("EXPORT",  message.getTitle());
        writer.beginObject();
        writer.name(Entries.Task._ID).value(message.getId());
        writer.name(Entries.Task.COLUMN_TITLE).value(message.getTitle());
        if (message.getDescription() != null) {
            writer.name(Entries.Task.COLUMN_DESCRIPTION).value(message.getDescription());
        } else {
            writer.name(Entries.Task.COLUMN_DESCRIPTION).value("");
        }
        if (message.getTime_end().getTime() > 0) {
            writer.name(Entries.Task.COLUMN_TIME_END).value(message.getTime_end().getTime());
        } else {
            writer.name(Entries.Task.COLUMN_TIME_END).value("");
        }
        if (message.getUrl_to_icon() != null) {
            writer.name(Entries.Task.COLUMN_URL_TO_ICON).value(message.getUrl_to_icon());
        } else {
            writer.name(Entries.Task.COLUMN_URL_TO_ICON).value("");
        }

            writer.name(Entries.Task.COLUMN_TIMESTAMP).value(message.getCreate_at().getTime());


        writer.endObject();
    }


    @Override
    public boolean moveDataBase(Context mContext) throws IOException {
        DataBaseHelper dba = new DataBaseHelperImpl(mContext);
        writeJsonStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(), pathToFile), false),dba.getTasks());
        Log.d("EXPORT", "SUCCEDED");
        return true;
    }
}
