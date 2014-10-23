package com.example.van.unteacided;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Van on 10/4/2014.
 */
public class TeaSQLiteHelper extends SQLiteOpenHelper{
    private static final String TABLE_TEAS = "teas";

    public static final String COLUMN_ENTRY_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TEMPF = "tempf";
    public static final String COLUMN_TEMPC = "tempc";
    public static final String COLUMN_STEEP = "steep";
    public static final String COLUMN_ACTIVATED = "activated";

    public static final String[] COLUMNS = {COLUMN_ENTRY_ID, COLUMN_NAME, COLUMN_TYPE, COLUMN_TEMPF, COLUMN_TEMPC, COLUMN_STEEP, COLUMN_ACTIVATED};

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TeaDB";

    public TeaSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TEA_TABLE = "CREATE table teas ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "type TEXT, " +
                "tempF INTEGER, " +
                "tempC INTEGER, " +
                "steep INTEGER, " +
                "activated INTEGER) ";

        db.execSQL(CREATE_TEA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS teas");

        this.onCreate(db);
    }

    public void insertTea(Tea t){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, t.getName() );
        cv.put(COLUMN_TYPE, t.getType());
        cv.put(COLUMN_TEMPF, t.getTempF());
        cv.put(COLUMN_TEMPC, t.getTempC());
        cv.put(COLUMN_STEEP, t.getSteepTime());
        cv.put(COLUMN_ACTIVATED, t.isActive());

        db.insert(TABLE_TEAS, null, cv);
        db.close();
    }

    public Tea getTea(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_TEAS, COLUMNS, String.valueOf(id), null, null, null, null);
        if(c != null)
            c.moveToFirst();
        Tea t = new Tea();
        t.setId(Integer.parseInt(c.getString(0)));
        t.setName(c.getString(1));
        t.setType(c.getString(2));
        t.setTempF(Integer.parseInt(c.getString(3)));
        t.setTempC(Integer.parseInt(c.getString(4)));
        t.setSteepTime(Integer.parseInt(c.getString(5)));
        t.setActivated(Integer.parseInt(c.getString(6)));

        return t;
    }

    public List<Tea> getAllTeas() {
        List<Tea> teas = new LinkedList<Tea>();

        String query = "SELECT * FROM " + TABLE_TEAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        Tea t = null;
        if (c.moveToFirst()) {
            while (c.isAfterLast() == false) {
                t = new Tea();
                t.setId(Integer.parseInt(c.getString(0)));
                t.setName(c.getString(1));
                t.setType(c.getString(2));
                t.setTempF(Integer.parseInt(c.getString(3)));
                t.setTempC(Integer.parseInt(c.getString(4)));
                t.setSteepTime(Integer.parseInt(c.getString(5)));
                t.setActivated(Integer.parseInt(c.getString(6)));
                teas.add(t);
                c.moveToNext();
            }
        }

        return teas;
    }

    public int updateTea(Tea t){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, t.getName() );
        cv.put(COLUMN_TYPE, t.getType());
        cv.put(COLUMN_TEMPF, t.getTempF());
        cv.put(COLUMN_TEMPC, t.getTempC());
        cv.put(COLUMN_STEEP, t.getSteepTime());
        cv.put(COLUMN_ACTIVATED, t.isActive());


        int i = db.update(TABLE_TEAS, cv, COLUMN_ENTRY_ID+" = ?", new String[] {String.valueOf(t.getId())});

        db.close();

        return i;
    }

    public void delete(Tea t){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TEAS, COLUMN_ENTRY_ID+" =?", new String[] {String.valueOf(t.getId())});
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAS, null, null);
    }

}
