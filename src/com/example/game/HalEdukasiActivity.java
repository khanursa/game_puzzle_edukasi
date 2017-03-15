package com.example.game;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HalEdukasiActivity extends Activity{
	private Bundle data;
	private ImageView Image1;
	private ImageView Image2; 
	private ImageView Image3;
	private TextView text;
	private String info;
	private String judul;
	private int edu;
	private int id;
	private MediaPlayer player;
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		setContentView(R.layout.haledukasi_activity);

		data = getIntent().getExtras();
		text = (TextView) findViewById(R.id.textJenisBuah);
		text.setText(judul);

		
		Image1 = (ImageView) findViewById(R.id.imagePreView);
		Image2 = (ImageView) findViewById(R.id.imageView2);
		Image3 = (ImageView) findViewById(R.id.imageView3);
		ImageV();

		player = MediaPlayer.create(this, edu);
		
		final Button BtnPlay = (Button) findViewById(R.id.buttonEdukasi);
		BtnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				while (!player.isPlaying())
				{
					player.start();
					BtnPlay.setClickable(false);
				}
				BtnPlay.setClickable(true);
				
			}
		});
		
		Button BtnBack = (Button) findViewById(R.id.buttonKembali);
		BtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				player.stop();
				finish();
			}
		});
		
		while (!player.isPlaying())
		{
			player.start();
			BtnPlay.setClickable(false);
		}
		BtnPlay.setClickable(true);
		
	}
	
	protected void ImageV()
	{
		LinkedList<Uri> availableImages = new LinkedList<Uri>();
		
		if(availableImages.isEmpty())
		{
			findInternalImages(availableImages);
		}
		
		if(availableImages.isEmpty())
		{
			Toast.makeText(this, "Tidak dapat memuat gambar", Toast.LENGTH_LONG).show();
			return;
		}

		Image1.setImageURI(availableImages.get(0));
		Image2.setImageURI(availableImages.get(1));
		Image3.setImageURI(availableImages.get(2));
	}

	protected void findInternalImages(List<Uri> list)
	{
		initialName(Integer.valueOf(data.getString("Edukasi")));
		String baseUri = getResourceBaseUri();
		Field[] fields = R.drawable.class.getFields();

		for(Field field: fields)
		{
			String name = field.getName();
			
			if(name.startsWith(info))
			{
				id = getResources().getIdentifier(name, "drawable", getPackageName());
				list.add(Uri.parse(baseUri + id));
			}
		}
	}	
	protected String getResourceBaseUri()
	{
		return "android.resource://" + HalEdukasiActivity.class.getPackage().getName() + "/"; 
	}
	
	protected void initialName(int IdNomor)
	{
		switch(IdNomor)
		{
		case 1:
			info = "apel";
			text.setText("APEL = APPEL");
			edu = R.raw.apel;
			break;
		case 2:
			info = "ceri";
			text.setText("CERI = CHERRY");
			edu = R.raw.ceri;
			break;
		case 3:
			info = "sirsak";
			text.setText("SIRSAK = SOURSOP");
			edu = R.raw.sirsak;
			break;
		case 4:
			info = "lemon";
			text.setText("LEMON = LEMON");
			edu = R.raw.lemon;
			break;
		case 5:
			info = "manggis";
			text.setText("MANGGIS = MANGOSTEEN");
			edu = R.raw.manggis;
			break;
		case 6:
			info = "jeruk";
			text.setText("JERUK = ORANGE");
			edu = R.raw.jeruk;
			break;
		case 7:
			info = "pir";
			text.setText("PIR = PEAR");
			edu = R.raw.pir;
			break;
		case 8:
			info = "pisang";
			text.setText("PISANG = BANANA");
			edu = R.raw.pisang;
			break;
		case 9:
			info = "stroberi";
			text.setText("STROBERI = STRAWBERRY");
			edu = R.raw.strowberi;
			break;
		case 10:
			info = "semangka";
			text.setText("SEMANGKA = WATERMELON");
			edu = R.raw.semangka;
			break;
		default:
			text.setText("Buah-Buahan");
			edu = R.raw.buahbuahan;
			break;
		}
	}
	
	@Override
	public void onBackPressed(){
            super.onBackPressed();
            player.stop();

     }
	
}


