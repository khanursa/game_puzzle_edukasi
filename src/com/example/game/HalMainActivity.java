package com.example.game;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class HalMainActivity extends Activity {
	
	private Bundle pesan = new Bundle() ;
	private boolean doubleBack;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		setContentView(R.layout.activity_main);
		
		Button btnEdukasi = (Button) findViewById(R.id.btnedUkasi);
		Button btnNewGame = (Button) findViewById(R.id.btnNewGame);		
		Button btnSkor = (Button) findViewById(R.id.buttonSkor);
		Button btnAbout = (Button) findViewById(R.id.buttonAbout);
		
		btnEdukasi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(), GridActivity.class);
				pesan.putString("main", "Edukasi");
				intent.putExtras(pesan);
				startActivity(intent);
			}
		});
				
		btnNewGame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(), GridActivity.class);
				pesan.putString("main", "Newgame");
				intent.putExtras(pesan);
				startActivity(intent);
			}
		});
		
		btnSkor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(), GridActivity.class);
				pesan.putString("main", "Skor");
				intent.putExtras(pesan);
				startActivity(intent);
			}
		});
		btnAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(), AboutActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed(){
		if (doubleBack) {
            super.onBackPressed();
            HalMainActivity.this.finish();
            return;
     }
     this.doubleBack = true;
     Toast.makeText(this, "Klik BACK 2x untuk keluar",
     Toast.LENGTH_SHORT).show();
     new Handler().postDelayed(new Runnable() {
    	 @Override
    	 public void run() {
    		 doubleBack = false;
    	 }
     }, 2000);
	}

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
