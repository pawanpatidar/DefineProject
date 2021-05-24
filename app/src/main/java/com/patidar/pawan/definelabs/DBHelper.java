package com.patidar.pawan.definelabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.patidar.pawan.definelabs.ui.AllMatches.MatchesModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static Context mContext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vanues_db";
    private static DBHelper mInstance = null;

    private static final String TABLE_VANUE = "vanue_table";
    private static final String COLUMN_VANUE_NAME = "vanueName";
    private static final String COLUMN_VANUE_ADDRESS = "vanueAddress";
    private static final String VANUE_ID = "id";


    public static DBHelper getmInstance(Context context){
        mContext =context;
        if(mInstance==null){
            mInstance = new DBHelper();
        }
        return mInstance;
    }

    public DBHelper(){
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_VANUE_TABLE = "CREATE TABLE " + TABLE_VANUE
                + "(" + VANUE_ID + " TEXT PRIMARY KEY,"
                + COLUMN_VANUE_NAME + " TEXT,"
                + COLUMN_VANUE_ADDRESS + " TEXT" + ")";


        db.execSQL(CREATE_VANUE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VANUE);
    }



    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VANUE,null, null);
    }

    ////=======================


    public boolean AddMatch(MatchesModel matchesModel){
        boolean response = false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VANUE_ID, matchesModel.getId());
            values.put(COLUMN_VANUE_NAME, matchesModel.getVanue());
            values.put(COLUMN_VANUE_ADDRESS, matchesModel.getAddress());

            long mlon = db.insert(TABLE_VANUE, null, values);
            if (mlon > 0) {

                response = true;
            } else {

                response = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


    public HashMap<String, String> getMatches() {
        HashMap<String ,String> matchHash = new HashMap<>();
        try {
            String selectQuery = "";
            selectQuery = "SELECT * FROM " + TABLE_VANUE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    try {
                        matchHash.put(cursor.getString(cursor.getColumnIndex(VANUE_ID)),cursor.getString(cursor.getColumnIndex(COLUMN_VANUE_NAME)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return matchHash;
    }

    public boolean deleteMatch(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean isdeleted= db.delete(TABLE_VANUE, VANUE_ID + "= '" + id+"'", null ) > 0;
        return isdeleted;
    }


}
