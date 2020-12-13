package com.example.swuljcityconductor;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BusDetails.db";
    public static final String TABLE_NAME = "bus_details_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NUMBER";
    public static final String COL_3 = "ROUTE";
    public static final String COL_4 = "CITY";
    public static final String COL_5 = "STATUS";
    Context cntxt;
    EditText edit;
    public DatabaseHelper(Context context, EditText edit1) {
        super(context, DATABASE_NAME, null, 1);
        cntxt = context;
        edit = edit1;

    }
    public void deleteDatabase() {
        // Are you sure? (y/n)
        final SQLiteDatabase db = this.getWritableDatabase();
        final File dbFile = new File(db.getPath());
        db.close();
        Toast.makeText(cntxt, "db deleted", Toast.LENGTH_SHORT).show();
        edit.setText("db deleted");
        if (dbFile.exists()) {
            SQLiteDatabase.deleteDatabase(dbFile);

        }
        //mOpenHelper = new DatabaseHelper(getContext());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NUMBER TEXT,ROUTE TEXT,CITY TEXT,STATUS INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String number,String route,String city,int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,route);
        contentValues.put(COL_4,city);
        contentValues.put(COL_5,status);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getLastData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "select * from "+ TABLE_NAME +" WHERE STATUS = 1";

        Cursor cursor = null ;

        try{
            cursor = sqLiteDatabase.rawQuery(query,null);
        }catch (SQLException ex)
        {
            Log.d("error",ex.toString());
        }
        return cursor ;
    }

    public Cursor getEnteredData(String data) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "select * from "+ TABLE_NAME +" WHERE NUMBER = "+data;

        Cursor cursor = null ;

        try{
            cursor = sqLiteDatabase.rawQuery(query,null);
        }catch (SQLException ex)
        {
            Log.d("error",ex.toString());
        }
        return cursor ;
    }

    public boolean updateData(String id,String number,String route,String city,int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,route);
        contentValues.put(COL_4,city);
        contentValues.put(COL_5,status);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] {id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
