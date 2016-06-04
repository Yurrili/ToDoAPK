package com.uj.yurrili.todoappandroid.db_managment.db_import_export;

import android.content.Context;

import java.net.MalformedURLException;

/**
 * Created by Yuri on 2016-06-04.
 */
public class ImportExportUses {

    private ImportExport impExpStr;
    private Context ctx;

    public void setImpExpWayStr(ImportExport impExpStr, Context ctx)
    {
        this.impExpStr = impExpStr;
        this.ctx = ctx;
    }

    public void doIt() throws MalformedURLException {
        impExpStr.moveDataBase(ctx);
    }

}