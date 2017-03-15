package com.example.game;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.database.DbaseDevice;

public class Splashscreen extends Activity {

    //Set waktu lama splashscreen
    private static int splashInterval = 10000;
    private DbaseDevice dbDevice;
    private TextView text;
    private String no = "1";
    private String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        dbDevice = new DbaseDevice(this);

        setContentView(R.layout.activity_splashscreen);
        text = (TextView) findViewById(R.id.textspscreen);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
            	dbDevice.openDBUser();
            	/** Cek record database device */
            	Cursor c = dbDevice.getDataUser(Long.parseLong(no));
        	    if (c.moveToFirst()) {        	    
        	    	nama = c.getString(0);
        	    	text.setText("Hai "+ nama + " apa kabar!!");
        	    	dbDevice.close();
        	    	Intent i = new Intent(Splashscreen.this, HalMainActivity.class);
        	    	startActivity(i);
        	    	finish();
                }else{
        	    
                Intent i = new Intent(Splashscreen.this, InputUserActivity.class);
                startActivity(i);
                finish();
                }
    	    	dbDevice.close();


                //jeda selesai Splashscreen
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub
            	Splashscreen.this.finish();
            }
        }, splashInterval);

    }

    ;

}