package com.example.game;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.example.database.DbaseDevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class HalSkorActivity extends Activity {
private Bundle data;
private ImageView ImageV;
private TextView text, txtPengguna;
private String msgNeighbor, msgNeighbort;
private int id;
private float opsi1, opsi2, opsi3, opsi0;
private RatingBar beginer, intermediate, advanced;
private DbaseDevice dbDevice;
private String NBuah;
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

        dbDevice = new DbaseDevice(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		setContentView(R.layout.halskor_activity);
		data = getIntent().getExtras();
		
		ImageV();
		text = (TextView) findViewById(R.id.textJudulSkor);
		txtPengguna = (TextView) findViewById(R.id.textUserScore);
		initialName(Integer.valueOf(data.getString("Skor")));
		beginer = (RatingBar) findViewById(R.id.ratingBarBeginer);
		beginer.setFocusable(false);
		intermediate = (RatingBar) findViewById(R.id.ratingBarIntermed);
		intermediate.setFocusable(false);
		advanced = (RatingBar) findViewById(R.id.ratingBarAdvanced);
		advanced.setFocusable(false);
		dbaseDevice();
		dbaseGame();
	}
	
	protected void dbaseGame(){
		msgNeighbort = "image_"+data.getString("Skor");
		opsi3 = 3;
		opsi2 = 2;
		opsi1 = 1;
		opsi0 = 0;
		dbDevice.openDBUser();
		Cursor c = dbDevice.getName(msgNeighbort);
		if (c.moveToFirst()){
			int B = Integer.parseInt(c.getString(1));
			if (B <= 5 && B > 0){
				beginer.setRating(opsi3);
			}else if(B > 5 && B <= 8){
				beginer.setRating(opsi2);
			}else if (B > 8){
				beginer.setRating(opsi1);
			}else{
				beginer.setRating(opsi0);
			}
			
			int I = Integer.parseInt(c.getString(2));
			if (I <= 60 && I > 0){
				intermediate.setRating(opsi3);
			}else if(I > 60 && I <= 180){
				intermediate.setRating(opsi2);
			}else if (I > 180){
				intermediate.setRating(opsi1);
			}else{
				intermediate.setRating(opsi0);
			}
			int A = Integer.parseInt(c.getString(3));
			if (A <= 300 && A > 0){
				advanced.setRating(opsi3);
			}else if(A > 300 && A <= 500){
				advanced.setRating(opsi2);
			}else if (A > 500){
				advanced.setRating(opsi1);
			}else{
				advanced.setRating(opsi0);
			}
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false);
			builder.setTitle("Peringatan");
			builder.setMessage("Anda belum memainkan puzzle untuk buah "+NBuah);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
					arg0.dismiss();
				}
			});
			builder.create().show();
		}
		beginer.setClickable(false);
		intermediate.setClickable(false);
		advanced.setClickable(false);
		dbDevice.close();
	}
	protected void dbaseDevice(){
		String no = "1";
		String nama;
		dbDevice.openDBUser();

    	/** Cek record database device */
    	Cursor c = dbDevice.getDataUser(Long.parseLong(no));
	    if (c.moveToFirst()) {        	    
	    	nama = c.getString(0);
	    	txtPengguna.setText("Hai "+ nama + " Bagaimana puzzlenya ^_^");
        }else{
        }
    	dbDevice.close();
	}
	
	protected void ImageV()
	{
		ImageV = (ImageView) findViewById(R.id.imageSkor);
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
		
		ImageV.setImageURI(availableImages.get(0));
	}

	protected void findInternalImages(List<Uri> list)
	{
		msgNeighbor = "agbr"+data.getString("Skor");
		String baseUri = getResourceBaseUri();
		Field[] fields = R.drawable.class.getFields();

		for(Field field: fields)
		{
			String name = field.getName();
			
			if(name.equals(msgNeighbor))
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
			text.setText("APEL = APPEL");
			NBuah = "Apel";
			break;
		case 2:
			text.setText("CERI = CHERRY");
			NBuah = "Ceri";
			break;
		case 3:
			text.setText("SIRSAK = SOURSOP");
			NBuah = "Sirsak";
			break;
		case 4:
			text.setText("LEMON = LEMON");
			NBuah = "Lemon";
			break;
		case 5:
			text.setText("MANGGIS = MANGOSTEEN");
			NBuah = "Manggis";
			break;
		case 6:
			text.setText("JERUK = ORANGE");
			NBuah = "Jeruk";
			break;
		case 7:
			text.setText("PIR = PEAR");
			NBuah = "Pir";
			break;
		case 8:
			text.setText("PISANG = BANANA");
			NBuah = "Pisang";
			break;
		case 9:
			text.setText("STROBERI = STRAWBERRY");
			NBuah = "Stroberi";
			break;
		case 10:
			text.setText("SEMANGKA = WATERMELON");
			NBuah = "Semangka";
			break;
		default:
			text.setText("Buah-Buahan");
			break;
		}
	}
}