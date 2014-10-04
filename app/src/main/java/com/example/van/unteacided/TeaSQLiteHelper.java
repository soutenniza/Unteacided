package com.example.van.unteacided;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Van on 10/4/2014.
 */
public class TeaSQLiteHelper extends SQLiteOpenHelper{
    private static final String TABLE_TEAS = "teas";

    public static final String COLUMN_ENTRY_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TEMPC = "tempc";
    public static final String COLUMN_TEMPF = "tempf";
    public static final String COLUMN_STEEP = "steep";
    public static final String COLUMN_ACTIVATED = "activated"

}
