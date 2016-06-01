package com.uj.yurrili.todoappandroid;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.sql.Timestamp;


/**
 * Created by Yuri on 2016-06-01.
 */
public class Utilities {

    public static String convertTime(Timestamp date){
        DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm yyyy-MM-dd ");
        return format.print(date.getTime());
    }

    public static Timestamp jodaToSQLTimestamp(LocalDateTime localDateTime) {
        return new Timestamp(localDateTime.toDateTime().getMillis());
    }

    public static LocalDateTime sqlTimestampToJodaLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.fromDateFields(timestamp);
    }
}
