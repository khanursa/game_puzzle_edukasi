package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class GridActivity extends Activity{
	
	private Bundle pesan;
	private String isiPesan;
	private Intent intent;
	
	Integer[] IDGambar = {
			R.drawable.agbr1,
			R.drawable.agbr2,
			R.drawable.agbr3,
			R.drawable.agbr4,
			R.drawable.agbr5,
			R.drawable.agbr6,
			R.drawable.agbr7,
			R.drawable.agbr8,
			R.drawable.agbr9,
			R.drawable.agbr10
	};
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grid_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		
		pesan = getIntent().getExtras();
		isiPesan = pesan.getString("main");

		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new ImageAdapter(this));
		TextView Judul = (TextView) findViewById(R.id.textGridmenu);
		
		if (isiPesan.equals("Edukasi")){
			Judul.setText("Menu Edukasi");
			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int posisi, long id){
					intent = new Intent(v.getContext(), HalEdukasiActivity.class );
					Bundle pesan = new Bundle();
					pesan.putString("Edukasi", ""+(posisi +1) );
					intent.putExtras(pesan);
					startActivityForResult(intent, 1);
				}
			});
		}
		else if(isiPesan.equals("Newgame"))
		{
			Judul.setText("Menu Pilih Gambar");
			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int posisi, long id){
					intent = new Intent(v.getContext(), GameActivity.class );
					Bundle pesan = new Bundle();
					pesan.putString("Game", ""+(posisi +1) );
					intent.putExtras(pesan);
					startActivityForResult(intent, 0);
				}
			});
		}else if(isiPesan.equals("Skor"))
		{
			Judul.setText("Menu Skor Pilih Gambar");
			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int posisi, long id){
					intent = new Intent(v.getContext(), HalSkorActivity.class );
					Bundle pesan = new Bundle();
					pesan.putString("Skor", ""+(posisi +1) );
					intent.putExtras(pesan);
					startActivityForResult(intent, 0);
				}
			});
		}else{
			Judul.setText("Lihat Skor Gambar");
		}
		
		
		
		
	}
	public class ImageAdapter extends BaseAdapter {
		private Context konteks;
		public ImageAdapter(Context c){
			konteks = c;
		}
		
		// memberikan nilai balik jumlah gambar
		public int getCount(){
			return IDGambar.length;
		}
		
		// memberikan nilai balik ID item
		public Object getItem(int posisi){
			return posisi;
		}
		
		public long getItemId(int posisi){
			return posisi;
		}
		
		// memberikan nilai balik sebuah image view
		public View getView(int posisi, View v, ViewGroup ortu){
			ImageView imageView;
			if (v == null){
				imageView = new ImageView(konteks);
				imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setPadding(5, 5, 5, 5);
			}else{
				imageView = (ImageView) v;
			}
			imageView.setImageResource(IDGambar[posisi]);
			return imageView;
		}
	}
}
