package com.example.game;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;

import java.util.ArrayList;

import com.example.database.DbaseDevice;

public class MainActivity extends Activity {
    DbaseDevice db;
    Button tombolCari;
    EditText editNama;
    ListView listViewData;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mainst);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		
        // Buat objek database
        db = new DbaseDevice(this);
        
        // Objek komponen
        editNama = (EditText) findViewById(R.id.editNama);
        listViewData = (ListView) findViewById(R.id.listViewDb);
        
        tombolCari = (Button) findViewById(R.id.buttonCari);
        tombolCari.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
        	    tampilkanData();
            }
            
        });
    }

    public void tampilkanData() {
        db.openDBUser();
        Cursor c = db.getName(editNama.getText().toString());
       
        // Buat array dinamis
        ArrayList<String> larik = new ArrayList<String>();
        
        if (c.moveToFirst()) {
            do {
                int idGame = c.getInt(0);
                String Scorebeginer = c.getString(1);
                String Scoreintermed = c.getString(2);
                String Scoreadvanced = c.getString(3);
                                      
                larik.add(Integer.toString(idGame) + ": " +
                          Scorebeginer + " - " + Scoreintermed + " - " +
                          Scoreadvanced);  
            } while (c.moveToNext());
        }
        else
        	larik.add("Data tidak ditemukan!");
        
        db.close();
        
        // Taruh larik ke Listview
     
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
           android.R.layout.simple_list_item_1, 
           larik);
         
        listViewData.setAdapter(adapter);
        
    }	
    
    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

}
