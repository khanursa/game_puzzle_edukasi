
package com.example.game;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import com.example.database.DbaseDevice;
import com.example.game.PuzzleView.ShowNumbers;
import com.example.game.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameActivity extends Activity
{
	protected static final int MIN_WIDTH = 2;
	protected static final int MAX_WIDTH = 4;

	protected static final String KEY_SHOW_NUMBERS = "showNumbers";
	protected static final String KEY_IMAGE_URI = "imageUri";
	protected static final String KEY_PUZZLE = "puzzle";
	protected static final String KEY_PUZZLE_SIZE = "puzzleSize";
	protected static final String KEY_MOVE_COUNT = "moveCount";

	protected static final String KEY_MOVE_BEST_PREFIX = "moveBest_";
	protected static final String KEY_MOVE_AVERAGE_PREFIX = "moveAvg_";
	protected static final String KEY_PLAYS_PREFIX = "plays_";
	protected static final int DEFAULT_SIZE = 3;	
	
	protected Bundle pesan;
	LinkedList<Uri> availableImages;
	
	private int id;
	private PuzzleView view;
	private Puzzle puzzle;
	private Options bitmapOptions;
	private int puzzleWidth = 1;
	private int puzzleHeight = 1;
	private int gw, gh;
	private Uri imageUri;
	private boolean portrait;
	private boolean expert;
	private DbaseDevice dbDevice;
		
	private String msgNeighbor;
	private Button btnAtas;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dbDevice = new DbaseDevice(this);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_game);
		bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = false;

		puzzle = new Puzzle();

		view = new PuzzleView(this, puzzle);
		RelativeLayout Relative = (RelativeLayout) findViewById(R.id.Layout1);
		Relative.addView(view);		
		btnAtas = (Button) findViewById(R.id.button_Solved);			

		if(imageUri == null)
		{
			newImage();	
			changeTiling();			

		}
		
		
		final Button btnBawah = (Button) findViewById(R.id.btnAcak);
		btnBawah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				scramble();
			}
		});



		ImageView ImgView = (ImageView) findViewById(R.id.image);
		ImgView.setImageURI(availableImages.get(0));

	}

	private void scramble()
	{
		puzzle.init(puzzleWidth, puzzleHeight);
		puzzle.scramble();
		view.invalidate();
		expert = view.getShowNumbers() == ShowNumbers.NONE;
	}
	
	protected void newImage()
	{
		availableImages = new LinkedList<Uri>();
		
		if(availableImages.isEmpty())
		{
			findInternalImages(availableImages);
		}
		
		if(availableImages.isEmpty())
		{
			Toast.makeText(this, "Tidak dapat memuat gambar", Toast.LENGTH_LONG).show();
			return;
		}
		
		loadBitmap(availableImages.get(0));
	}

	protected void findInternalImages(List<Uri> list)
	{
		pesan = getIntent().getExtras();
		msgNeighbor = "image_"+pesan.getString("Game");
		String baseUri = getResourceBaseUri();
		Field[] fields = R.drawable.class.getFields();

		for(Field field: fields)
		{
			String name = field.getName();
			
			if(name.startsWith(msgNeighbor))
			{
				id = getResources().getIdentifier(name, "drawable", getPackageName());
					list.add(Uri.parse(baseUri + id));
			}
		}
	}


	/* ~~~~~~~ */
	private void solved()
	{		
		puzzle.init(puzzleWidth, puzzleHeight);
		puzzle.Algoritmaastar();
		view.invalidate();
		onFinish();

	}
	
	protected void loadBitmap(Uri uri)
	{
		try
		{
			Options o = new Options();
			o.inJustDecodeBounds = true;

			InputStream imageStream = getContentResolver().openInputStream(uri);
			BitmapFactory.decodeStream(imageStream, null, o);

			int targetWidth = view.getTargetWidth();
			int targetHeight = view.getTargetHeight();

			if(o.outWidth > o.outHeight && targetWidth < targetHeight)
			{
				int i = targetWidth;
				targetWidth = targetHeight;
				targetHeight = i;
			}

			if(targetWidth < o.outWidth || targetHeight < o.outHeight)
			{
				double widthRatio = (double) targetWidth / (double) o.outWidth;
				double heightRatio = (double) targetHeight / (double) o.outHeight;
				double ratio = Math.max(widthRatio, heightRatio);

				o.inSampleSize = (int) Math.pow(2, (int) Math.round(Math.log(ratio) / Math.log(0.5)));
			}
			else
			{
				o.inSampleSize = 1;
			}

			o.inScaled = false;
			o.inJustDecodeBounds = false;

			imageStream = getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, o);

			if(bitmap == null)
			{
				Toast.makeText(this, "Tidak dapat memuat gambar", Toast.LENGTH_LONG).show();
				return;
			}
			
			int rotate = 0;

			Cursor cursor = getContentResolver().query(uri, new String[] {MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
			
			if(cursor != null)
			{
				try
				{
					if(cursor.moveToFirst())
					{
						rotate = cursor.getInt(0);
						
						if(rotate == -1)
						{
							rotate = 0;
						}
					}
				}
				finally
				{
					cursor.close();
				}
			}
			
			if(rotate != 0)
			{
				Matrix matrix = new Matrix();
				matrix.postRotate(rotate);
				
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			}
			
			setBitmap(bitmap);
			imageUri = uri;
		}
		catch(FileNotFoundException ex)
		{
			Toast.makeText(this, MessageFormat.format("Tidak dapat memuat gambar", ex.getMessage()), Toast.LENGTH_LONG).show();
			return;
		}
	}

	private void setBitmap(Bitmap bitmap)
	{
		portrait = bitmap.getWidth() < bitmap.getHeight();

		view.setBitmap(bitmap);
		setPuzzleSize(Math.min(puzzleWidth, puzzleHeight), true);

		setRequestedOrientation(portrait ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
	}

	private void changeTiling()
	{			
		int width = 0;
		int height = 0;
		float ratio = getImageAspectRatio();
		
		if(ratio < 1)
		{
			ratio = 1f / ratio;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pilih Level Puzzle");

		builder.setCancelable(false);
		String[] items = new String[MAX_WIDTH - MIN_WIDTH + 1];
		int selected = 0;
		
		for(int i = 0; i < items.length; i++)
		{

			
			if(portrait)
			{
				width = i + MIN_WIDTH;
				height = (int) (width * ratio);
			}
			else
			{
				height = i + MIN_WIDTH;
				width = (int) (height * ratio);
			}
			
			items[i] = sizeToString(width, height);
			
			if(i + MIN_WIDTH == Math.min(puzzleWidth, puzzleHeight))
			{
				selected = i;
			}
			
		}
		
		builder.setSingleChoiceItems(items, selected, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				setPuzzleSize(which + MIN_WIDTH, false);
				dialog.dismiss();			
				if (puzzleWidth != 2 || puzzleHeight !=2){
					btnAtas.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							solved();
						}
					});
				}else{					
					btnAtas.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplication(), "Maaf, Tidak dapat digunakan dilevel ini", Toast.LENGTH_SHORT).show();
						btnAtas.setClickable(false);
					}
				});
				}
			}
		});
		builder.create().show();
		
	}

	private float getImageAspectRatio()
	{
		Bitmap bitmap = view.getBitmap();
		
		if(bitmap == null)
		{
			return 1;
		}
		
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		
		return width / height;
	}

	protected void setPuzzleSize(int size, boolean scramble)
	{
		float ratio = getImageAspectRatio();
		
		if(ratio < 1)
		{
			ratio = 1f /ratio;
		}
		
		int newWidth;
		int newHeight;
		
		if(portrait)
		{
			newWidth = size;
			newHeight = (int) (size * ratio); 
		}
		else
		{
			newWidth = (int) (size * ratio); 
			newHeight = size;
		}
		
		if(scramble || newWidth != puzzleWidth || newHeight != puzzleHeight)
		{
			puzzleWidth = newWidth;
			puzzleHeight = newHeight;
			scramble();
		}
	}

	protected String sizeToString(int width, int height)
	{
		String level;
	if (width == 2 |height == 2 ){
		gw = 2;
		gh = 2;
		level = "beginer";
	}else if (width == 3 |height == 3 ){
		gw = 3;
		gh = 3;
		level = "intermediate";
	}else{
		gw = 4;
		gh = 4;
		level = "advanced";
	}		

	return MessageFormat.format(""+ level + " {0} x {1}", width, height);
	}
	
	protected SharedPreferences getPreferences()
	{
		return getSharedPreferences(GameActivity.class.getName(), Activity.MODE_PRIVATE);
	}
	@Override
	protected void onStop()
	{
		super.onStop();

		savePreferences();
	}

	public PuzzleStats updateStats()
	{
		SharedPreferences prefs = getPreferences();
		Editor editor = prefs.edit();

		int i = (expert ? 10000 : 0) +
				Math.min(puzzle.getWidth(), puzzle.getHeight()) * 100 +
				Math.max(puzzle.getWidth(), puzzle.getHeight());
		String index = String.valueOf(i);
		
		int plays = prefs.getInt(KEY_PLAYS_PREFIX + index, 0);
		int best = prefs.getInt(KEY_MOVE_BEST_PREFIX + index, 0);
		float avg = prefs.getFloat(KEY_MOVE_AVERAGE_PREFIX + index, 0);

		plays++;
		boolean isNewBest = best == 0 || best > puzzle.getMoveCount();

		if(isNewBest)
		{
			best = puzzle.getMoveCount();
		}

		avg = (avg * (plays - 1) + puzzle.getMoveCount()) / (float) plays;

		editor.putInt(KEY_PLAYS_PREFIX + index, plays);
		editor.putInt(KEY_MOVE_BEST_PREFIX + index, best);
		editor.putFloat(KEY_MOVE_AVERAGE_PREFIX + index, avg);

		editor.commit();

		return new PuzzleStats(plays, best, avg, isNewBest);
	}

	protected boolean loadPreferences()
	{
		SharedPreferences prefs = getPreferences();
		
		try
		{
	
			String s = prefs.getString(KEY_IMAGE_URI, null);
	
			if(s == null)
			{
				imageUri = null;
			}
			else
			{
				loadBitmap(Uri.parse(s));
			}
	
			int size = prefs.getInt(KEY_PUZZLE_SIZE, 0);
			s = prefs.getString(KEY_PUZZLE, null);
	
			if(size > 0 && s != null)
			{
				String[] tileStrings = s.split("\\;");
	
				if(tileStrings.length / size > 1)
				{
					setPuzzleSize(size, false);
					puzzle.init(puzzleWidth, puzzleHeight);
		
					int[] tiles = new int[tileStrings.length];
		
					for(int i = 0; i < tiles.length; i++)
					{
						try
						{
							tiles[i] = Integer.parseInt(tileStrings[i]);
						}
						catch(NumberFormatException ex)
						{
						}
					}
					
					puzzle.setTiles(tiles);
				}
			}
	
			puzzle.setMoveCount(prefs.getInt(KEY_MOVE_COUNT, 0));
	
			return prefs.contains(KEY_SHOW_NUMBERS);
		}
		catch(ClassCastException ex)
		{
			// ignore broken settings
			return false;
		}
	}

	protected String getResourceBaseUri()
	{
		return "android.resource://" + GameActivity.class.getPackage().getName() + "/"; 
	}

	protected void savePreferences()
	{
		SharedPreferences prefs = getPreferences();
		Editor editor = prefs.edit();

		if(imageUri == null)
		{
			editor.remove(KEY_IMAGE_URI);
		}
		else
		{
			editor.putString(KEY_IMAGE_URI, imageUri.toString());
		}

		StringBuilder sb = null;

		for(int tile: puzzle.getTiles())
		{
			if(sb == null)
			{
				sb = new StringBuilder();
			}
			else
			{
				sb.append(';');
			}

			sb.append(tile);
		}

		editor.putInt(KEY_PUZZLE_SIZE, Math.min(puzzleWidth, puzzleHeight));
		editor.putString(KEY_PUZZLE, sb.toString());
		editor.putInt(KEY_MOVE_COUNT, puzzle.getMoveCount());

		editor.commit();
	}

		public void showStats(PuzzleStats stats)
	{
		String type = sizeToString(puzzleWidth, puzzleHeight);

		pesan = getIntent().getExtras();
		
		String msg;

		if(puzzle.isSolved())
		{
			msg = MessageFormat.format(getString(R.string.finished_type_expert_puzzle_in_n_moves), type, expert ? 1 : 0, puzzle.getMoveCount());
		}
		else
		{
			msg = MessageFormat.format(getString(R.string.type_expert_puzzle_n_moves_so_far), type, expert ? 1 : 0, puzzle.getMoveCount());
		}

		msg = MessageFormat.format(getString(R.string.message_stats_best_avg_plays), msg, stats.getBest(), stats.getAvg(), stats.getPlays());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(!puzzle.isSolved() ? "Status" : stats.isNewBest() ? "New Record" : "Solved!");
		builder.setMessage(msg);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				GameActivity.this.finish();
			}
		});
		builder.create().show();
		
		String beginer;
		String intermediate;
		String advanced;
		String move = String.valueOf(puzzle.getMoveCount());
		int idGame =0;
		int w = puzzle.getWidth();
		int h = puzzle.getHeight();
		pesan = getIntent().getExtras();
		msgNeighbor = "image_"+pesan.getString("Game");
		dbDevice.openDBUser();
    	Cursor c = dbDevice.getName(msgNeighbor);
    	if(c.moveToFirst()){
    		idGame = c.getInt(0);

    		beginer = c.getString(1);
    		intermediate = c.getString(2);
    		advanced = c.getString(3);
    		
	    	if ((w == 2 && h == 2) && (Integer.parseInt (move) <= Integer.parseInt(beginer) || Integer.parseInt(beginer) == 0)) {
	        	beginer = move;
	    	}else if((w == 3 && h == 3) && (Integer.parseInt (move) <= Integer.parseInt(intermediate) || Integer.parseInt(intermediate) == 0)){
	        	intermediate = move;
	    	}else if((w == 4 && h == 4) && (Integer.parseInt (move) <= Integer.parseInt(advanced) || Integer.parseInt(advanced) == 0)){
	    		advanced = move;
	    	}
	    	
			dbDevice.updateData(idGame, msgNeighbor, beginer, intermediate, advanced);
    	}else{
    		// membuat data baru
    		if (w == 2 || h == 2){
    			beginer = String.valueOf(puzzle.getMoveCount());
    			intermediate = "0";
    			advanced = "0";
    		}else if(w == 3 || h == 3){
    			beginer = "0";
    			intermediate = String.valueOf(puzzle.getMoveCount());
    			advanced = "0";
    		}else{
    			beginer = "0";
    			intermediate = "0";
    			advanced = String.valueOf(puzzle.getMoveCount());
    		}
    		dbDevice.insertData(msgNeighbor, beginer, intermediate, advanced);
    	}
    	dbDevice.close();
	}
	
	
	public void playSound(int soundId)
	{
		MediaPlayer player = MediaPlayer.create(this, soundId);
		player.start();
	}

	public void onFinish()
	{
		PuzzleStats stats = updateStats();
		playSound(stats.isNewBest() ? R.raw.applause : R.raw.applause);
		showStats(stats);
	}
	
	
	public PuzzleView getView()
	{
		return view;
	}
	
	public Puzzle getPuzzle()
	{
		return puzzle;
	}
}
