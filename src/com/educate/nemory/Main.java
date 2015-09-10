package com.educate.nemory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener {
	private Database db = new Database(this); // INSTANTIATE DataBase Class
	
	// INTENTS
	private Intent optionsIntent, helpIntent, playersIntent, notesIntent, scoreBoardIntent, xylophoneIntent;

	// BUTTONS
	private Button btnStartGame, btnOptions, btnScoreBoard, btnPlayXylophone, btnNotesToStudy, btnHelp;

	// LAYOUTS
	private RelativeLayout rlMain;
	private LinearLayout llButtons, llTouchAnyWhere;

	// MEDIAPLAYER OBJECTS
	private MediaPlayer mpIntro, mpWelcome, mpHelp, mpSoundsIsOn, mpSoundsIsOff, mpScores, mpOptions, mpPlay, mpNotes, mpPlayXylophone;

	// SOUNDPOOL OBJECTS
	private SoundPool spTap;

	// SOUNDPOOL IDs
	private int spTapSoundID = 0;

	// BOOLEAN
	private boolean sounds = true;
	
	// PREFERENCES
	private SharedPreferences prefs;
	
	// ANIMATION
	private Animation anim;
	
	private ImageView ivLogoText;
	
	private TextView tvTouchToContinue;

	// Called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// SET FULLSCREEN MODE
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// SET LAOUT FOR THIS ACTIVITY
		setContentView(R.layout.main);

		// INITIALIZE
		Initialize();
		
		llButtons.setVisibility(View.INVISIBLE);
		
		rlMain.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				llTouchAnyWhere.setVisibility(View.GONE);
				tvTouchToContinue.setVisibility(View.GONE);
				llButtons.setVisibility(View.VISIBLE);
				rlMain.setBackgroundResource(R.drawable.background);
				rlMain.setOnClickListener(null); // unregister listener of lyMain
				if (sounds) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);
			}
		});
		
		SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	    sensorManager.registerListener(new SensorEventListener() {
	        int orientation=-1;;

	        public void onSensorChanged(SensorEvent event) {
	            if (event.values[1]<6.5 && event.values[1]>-6.5) {
	                if (orientation!=1) {
	                    Log.d("Sensor", "Landscape");
	                    //rlMain.setBackgroundResource(R.drawable.background_land);
	                }
	                orientation=1;
	            } else {
	                if (orientation!=0) {
	                    Log.d("Sensor", "Portrait");
	                   // rlMain.setBackgroundResource(R.drawable.background);
	                }
	                orientation=0;
	            }
	        }

	        public void onAccuracyChanged(Sensor sensor, int accuracy) {

	        }
	    }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		// SET SOUND TO LOOP FOREVERs
		if (sounds) mpIntro.setLooping(true);
		
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 100;
		v.vibrate(milliseconds);
		
		if (sounds) mpWelcome.start();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		prefs.registerOnSharedPreferenceChangeListener( new SharedPreferences.OnSharedPreferenceChangeListener() { 
			 public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
				 if(prefs.getBoolean(Constants.KEY_PREFS_SOUND, true)) sounds = true; else sounds = false;
				 Log.d("PREFS", "CHANGED, Sounds = " + sounds);
				 if(sounds){
					 mpSoundsIsOn.start();
				 }else{
					 mpSoundsIsOff.start();
				 }
			 }
		 });
		
		if (sounds) mpIntro.start();
		Log.d("MainMenu", "STARTED");
	}

	@Override
	protected void onPause() {
		// HAPPENS WHEN THIS ACTIVITY IS NOT SHOWN
		// OR THE APP IS IN THE BACKGROUND MODE
		super.onPause();
		if (sounds) mpIntro.pause();
		Log.d("MainMenu", "PAUSED");
	}

	@Override
	protected void onResume() {
		// HAPPENS WHEN THE APPLICATION WAS RE OPENED
		super.onResume();
		if (sounds) mpIntro.start();
		//AnimateButtons();
		Log.d("MainMenu", "RESUMED");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.open();
		db.ResetPlayersScore();
		db.close();
		Log.d("MainMenu", "DESTROYED");
	}

	// DYNAMIC onClickListeners FOR ALL BUTTONS
	public void onClick(View v) {
		// FOR EVERY CLICK PLAY THE TAP SOUND
		if (sounds) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);
		
		switch (v.getId()) {

		// IF BUTTON btnStartGame was clicked
		case R.id.btnStartGame:
			startActivity(playersIntent);
			if (sounds) mpPlay.start();
			break;

		// IF BUTTON btnOptions was clicked
		case R.id.btnOptions:
			startActivity(optionsIntent);
			if (sounds) mpOptions.start();
			break;
			
		// IF BUTTON btnOptions was clicked
		case R.id.btnScoreBoard:
			startActivity(scoreBoardIntent);
			if (sounds) mpScores.start();
			break;
			
		case R.id.btnPlayXylophone:
			startActivity(xylophoneIntent);
			//if (sounds) mpScores.start();
			break;
		
		case R.id.btnNotesToStudy :
			startActivity(notesIntent);
			if (sounds) mpNotes.start();
			break;
			
		case R.id.btnHelp:
			startActivity(helpIntent);
			if (sounds) mpHelp.start();
			break;

		}
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	private void Initialize() {
		// INTENTS
		optionsIntent = new Intent(this, Options.class);
		helpIntent = new Intent(this, Help.class);
		playersIntent = new Intent(this, Players.class);
		scoreBoardIntent = new Intent(this, HighScores.class);
		notesIntent = new Intent(this, StudyNotes.class);
		xylophoneIntent = new Intent(this, Xylophone.class);
		
		// BUTTONS
		btnStartGame = (Button) findViewById(R.id.btnStartGame);
		btnOptions = (Button) findViewById(R.id.btnOptions);
		btnScoreBoard = (Button) findViewById(R.id.btnScoreBoard);
		btnPlayXylophone = (Button) findViewById(R.id.btnPlayXylophone);
		btnNotesToStudy = (Button) findViewById(R.id.btnNotesToStudy);
		btnHelp = (Button) findViewById(R.id.btnHelp);
		
		ivLogoText = (ImageView) findViewById(R.id.ivLogoText);	
		tvTouchToContinue = (TextView) findViewById(R.id.tvTouchToContinue);

		// LAYOUTS
		rlMain = (RelativeLayout) findViewById(R.id.rlMain);
		llButtons = (LinearLayout) findViewById(R.id.llButtons);
		llTouchAnyWhere = (LinearLayout) findViewById(R.id.llTouchToContinue);
		
		anim = new TranslateAnimation(0.0f, 0.0f, -200, 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));
		anim.setStartOffset(300);
		
		ivLogoText.startAnimation(anim);
		//AnimateButtons();

		// SET BUTTONS's ONCLICKLISTENERS
		Button[] buttons = { btnStartGame, btnOptions, btnScoreBoard, btnPlayXylophone, btnNotesToStudy, btnHelp};
		for (Button b : buttons) {
			b.setOnClickListener(this);
			b.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));
		}
		tvTouchToContinue.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));

		// MEDIA SOUNDS
		mpIntro = MediaPlayer.create(this, R.raw.intro);
		mpHelp = MediaPlayer.create(this, R.raw.z_help);
		mpScores = MediaPlayer.create(this, R.raw.z_scores);
		mpOptions = MediaPlayer.create(this, R.raw.z_options);
		mpPlay = MediaPlayer.create(this, R.raw.z_play);
		mpNotes = MediaPlayer.create(this, R.raw.z_notes);
		mpWelcome = MediaPlayer.create(this, R.raw.z_welcoome);
		mpSoundsIsOn =  MediaPlayer.create(this, R.raw.z_sound_on);
		mpSoundsIsOff =  MediaPlayer.create(this, R.raw.z_sound_off);

		// SOUNDPOOL SOUNDS
		spTap = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spTapSoundID = spTap.load(this, R.raw.tap, 0);

		// PREFERENCES
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sounds = (prefs.getBoolean(Constants.KEY_PREFS_SOUND, true)) ? true : false;
	}
	
	private void AnimateButtons(){
		Button[] buttons = { btnStartGame, btnOptions, btnScoreBoard, btnPlayXylophone, btnNotesToStudy, btnHelp};
		for (Button b : buttons) {
			b.startAnimation(anim);
		}
	}


}