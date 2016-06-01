package com.uj.yurrili.todoappandroid;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Yuri on 2016-06-01.
 */
public class Utilities {

    public static String convertTime(Timestamp date){
        Format format = new SimpleDateFormat("HH:mm dd/MM/yyyy ");
        return format.format(date);
    }
}
