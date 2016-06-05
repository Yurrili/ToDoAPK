package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;

import java.net.MalformedURLException;

/**
 * Created by Yuri on 2016-06-04.
 */
public interface ImportExport {
    String pathToFile = "backUp_ToDo_f.json";
    boolean moveDataBase(Context mContext) throws MalformedURLException;
}
