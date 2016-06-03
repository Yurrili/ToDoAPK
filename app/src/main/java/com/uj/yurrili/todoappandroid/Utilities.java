package com.uj.yurrili.todoappandroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Pair;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.sql.Timestamp;


/**
 * Created by Yuri on 2016-06-01.
 */
public class Utilities {

    public static final int currentapiVersion = android.os.Build.VERSION.SDK_INT;

//    public static String convertTime(Timestamp date){
//        DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm yyyy-MM-dd ");
//        return format.print(date.getTime());
//    }

    public static Timestamp jodaToSQLTimestamp(LocalDateTime localDateTime) {
        return new Timestamp(localDateTime.toDateTime().getMillis());
    }

    public static LocalDateTime sqlTimestampToJodaLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.fromDateFields(timestamp);
    }

    public static Pair<String,String>  convertTime(Timestamp datetime){
        DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTimeFormatter timeStringFormat = DateTimeFormat.forPattern("HH:mm");
        String date = dateStringFormat.print(datetime.getTime());
        String time = timeStringFormat.print(datetime.getTime());
        return new Pair<>(time, date);
    }

    public static String setDatePick(int year, int month, int day){
        String d, m;

        if( day < 10){
            d = "0"+day;
        }else {
            d = day +"";
        }

        month = month +1;

        if( month < 10){
            m = "0"+ month;
        }else {
            m = month +"";
        }
        return d + "/" + m +  "/" + year;
    }
}
