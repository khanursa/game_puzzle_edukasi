package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DbaseDevice {
    private SQLiteDatabase db;
    private final Context konteks;
    private final DBHelperDevice dbhelperDevice;
    
    // Konstruktor
    public DbaseDevice(Context k) {
    	konteks = k;
    	dbhelperDevice = new DBHelperDevice(konteks, Konstanta.NAMA_DB,
    			                null, Konstanta.VERSI_DB);
    }
    
    // Membuka database device
    public void openDBUser() throws SQLiteException {
        try {
        	db = dbhelperDevice.getWritableDatabase();
        }
        catch (SQLiteException e) {
        	db = dbhelperDevice.getReadableDatabase();
        }
    }
    
    // Menutup database
    public void close() {
    	db.close();
    }
    
    // Mengambil data user berdasarkan kunci
    public Cursor getDataUser(long kunci) {
    	Cursor c = db.query(Konstanta.NAMA_TABEL2, 
    			           new String[] { Konstanta.NAMA },
        		           Konstanta.ID_USER + "=" + kunci,
        		           null, null, null, null);
    	if (c != null)
        	c.moveToFirst();
        
        return c;
    }
    
    // Menyisipkan data user
    public long insertUser(String nama) {
    	try {
    		ContentValues dataBaru = new ContentValues();
    		dataBaru.put(Konstanta.NAMA, nama);
    		
    		return db.insert(Konstanta.NAMA_TABEL2, null, dataBaru);
    	}
    	catch (SQLiteException e) {
    		return -1;
    	}
    }
       
    // Mengubah data user
    public boolean updateDataUSer(long kunci, String nama) {
    	ContentValues cv = new ContentValues();
    	cv.put(Konstanta.NAMA, nama);
    	
    	return db.update(Konstanta.NAMA_TABEL2, cv, 
    			         Konstanta.ID_USER + "=" + kunci, null) > 0;
    }
    // Menyisipkan data
    public long insertData(String nama, String beginer, String intermediate, String advanced) {
    	try {
    		ContentValues dataBaru = new ContentValues();
    		dataBaru.put(Konstanta.NAMA_BUAH, nama);
    		dataBaru.put(Konstanta.SCORE_BEGINER, beginer);
    		dataBaru.put(Konstanta.SCORE_INTERMEDIATE, intermediate);
    		dataBaru.put(Konstanta.SCORE_ADVANCED, advanced);
    		
    		return db.insert(Konstanta.NAMA_TABEL1, null, dataBaru);
    	}
    	catch (SQLiteException e) {
    		return -1;
    	}
    }
    
    // Menghapus data menurut kunci
    public boolean deleteData(long kunci) {
    	return db.delete(Konstanta.NAMA_TABEL1, 
    			         Konstanta.ID_GAME +  "=" + kunci, null) > 0;
    }
    
    // Mengambil seluruh data
    public Cursor getAllData() {
          return db.query(Konstanta.NAMA_TABEL1, 
        		        new String[] {Konstanta.ID_GAME,
        		  					  Konstanta.NAMA, 
        		                      Konstanta.SCORE_BEGINER, 
        		                      Konstanta.SCORE_INTERMEDIATE,
        		                      Konstanta.SCORE_ADVANCED }, 
        		        null, null, null, null, null);
    }
   
    // Mengambil data berdasarkan penggalan nama
    public Cursor getName(String nama_buah) {
    	Cursor c = db.query(Konstanta.NAMA_TABEL1, 
    			           new String[] { Konstanta.ID_GAME,
    									  Konstanta.SCORE_BEGINER, 
						                  Konstanta.SCORE_INTERMEDIATE,
						                  Konstanta.SCORE_ADVANCED }, 
        		           Konstanta.NAMA_BUAH + " LIKE '%" + nama_buah + "%'",
        		           null, null, null, null);
    	if (c != null)
        	c.moveToFirst();
        
        return c;
    }
    
    // Mengambil data berdasarkan kunci
    public Cursor getData(long kunci) {
    	Cursor c = db.query(Konstanta.NAMA_TABEL1, 
    			           new String[] { Konstanta.NAMA_BUAH,
    									  Konstanta.SCORE_BEGINER, 
                  						  Konstanta.SCORE_INTERMEDIATE,
                  						  Konstanta.SCORE_ADVANCED },
        		           Konstanta.ID_GAME + "=" + kunci,
        		           null, null, null, null);
    	if (c != null)
        	c.moveToFirst();
        
        return c;
    }
    
    // Mengubah data
    public boolean updateData(long kunci, String nama_buah,String beginer,
    		                  String intermediate, String advanced) {
    	ContentValues cv = new ContentValues();
    	cv.put(Konstanta.NAMA_BUAH, nama_buah);
    	cv.put(Konstanta.SCORE_BEGINER, beginer);
    	cv.put(Konstanta.SCORE_INTERMEDIATE, intermediate);
    	cv.put(Konstanta.SCORE_ADVANCED, advanced);
    	
    	return db.update(Konstanta.NAMA_TABEL1, cv, 
    			         Konstanta.ID_GAME + "=" + kunci, null) > 0;
    }
}
