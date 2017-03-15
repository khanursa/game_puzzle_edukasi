package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelperDevice extends SQLiteOpenHelper {
	// Berisi perintah SQL untuk menciptakan tabel bernama game dan deviceuser
    private final static String BUAT_TABEL_DEVICE = "create table " +
        Konstanta.NAMA_TABEL2 + " (" +
        Konstanta.ID_USER + " integer primary key autoincrement, " +
        Konstanta.NAMA + " text not null);";

    private final static String BUAT_TABEL_GAME = "create table " +
        Konstanta.NAMA_TABEL1 + " (" +
        Konstanta.ID_GAME + " integer primary key autoincrement, " +
        Konstanta.NAMA_BUAH + " text not null, " +
        Konstanta.SCORE_BEGINER + " text not null, " +
        Konstanta.SCORE_INTERMEDIATE + " text not null, " +
        Konstanta.SCORE_ADVANCED + " text not null);";
    
    public DBHelperDevice(Context konteks, String nama, 
    		        CursorFactory f, int versi) {
        super(konteks, nama, f, versi);    	
    }
    
    public void onCreate(SQLiteDatabase db) {
    	try {
    		db.execSQL(BUAT_TABEL_DEVICE);
    		db.execSQL(BUAT_TABEL_GAME);
    	}
    	catch (SQLiteException e) {
    	}
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int versiLama,
    		                                 int versiBaru) {
    	db.execSQL("drop tabel if exists " +
    		       Konstanta.NAMA_TABEL2);

    	db.execSQL("drop tabel if exists " +
    		       Konstanta.NAMA_TABEL1);
    	onCreate(db);
    }
}
