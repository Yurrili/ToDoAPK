package com.uj.yurrili.todoappandroid.db_managment;

import android.provider.BaseColumns;

/**
 * Created by Yuri on 2016-06-01.
 */

public class Entries {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE =" INTEGER";

    public Entries() {
    }

    private static final String SQL_CREATE_ENTRIES_Task =
            "CREATE TABLE " + Task.TABLE_NAME + " (" +
                    Task._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Task.COLUMN_TITLE + TEXT_TYPE + " NOT NULL " + COMMA_SEP +
                    Task.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    Task.COLUMN_URL_TO_ICON + TEXT_TYPE + COMMA_SEP +
                    Task.COLUMN_TIME_END + INTEGER_TYPE + COMMA_SEP +
                    Task.COLUMN_TIMESTAMP + INTEGER_TYPE +
                    " )";

    // timestamp in integer, retrieving timestamps by getLong()

    public static String getSQL_CREATE_ENTRIES_Task() {
        return SQL_CREATE_ENTRIES_Task;
    }

    public static abstract class Task implements BaseColumns {
        public static final String TABLE_NAME = "Task";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_URL_TO_ICON = "URL_to_icon";
        public static final String COLUMN_TIME_END = "Time_end";
        public static final String COLUMN_TIMESTAMP = "Timestamp";
    }

    public static final int taskColumns = 6;

    public static final String[] selectAllTasks = new String [] { Task._ID, Task.COLUMN_TITLE, Task.COLUMN_DESCRIPTION,
            Task.COLUMN_URL_TO_ICON, Task.COLUMN_TIME_END, Task.COLUMN_TIMESTAMP};
}
