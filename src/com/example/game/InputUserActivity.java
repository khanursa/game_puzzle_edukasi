package com.example.game;

import com.example.database.DbaseDevice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputUserActivity extends Activity {
    private DbaseDevice dbDevice;
    private EditText text;
    private Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        dbDevice = new DbaseDevice(this);
        setContentView(R.layout.activity_input_user);
        text = (EditText) findViewById(R.id.editTxtInputUser);
        simpan = (Button) findViewById(R.id.btnSimpanInputUser);
        simpan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(text.getText().toString().equals("")){
					Toast.makeText(getApplication(), "Harap masukan nama anda", Toast.LENGTH_SHORT).show();
				}else{
					dbDevice.openDBUser();
					if(text.getText().equals("")){
						Toast.makeText(getApplication(), "Maaf masukan dulu nama anda", Toast.LENGTH_SHORT).show();
						return;
					}else{
					dbDevice.insertUser(text.getText().toString());
	    	    	Intent i = new Intent(InputUserActivity.this, HalMainActivity.class);
	    	    	startActivity(i);
					finish();
					}
					dbDevice.close();
				}
			}
		});
    }
    
    @Override
    public void onBackPressed(){
    	
    }
}
