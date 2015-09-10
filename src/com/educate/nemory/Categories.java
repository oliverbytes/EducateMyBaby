package com.educate.nemory;

import com.educate.nemory.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class Categories extends Activity implements OnClickListener{
	
	// BUTTONS
	private Button btnImageIdentification, btnSoundIdentification, btnPinPointing;
	
	// INTENT
	private Intent subCatIntent, gameIntent;

	// SOUNDPOOL OBJECTS
	private SoundPool spTap;
	
	// SOUNDPOOL IDs
	private int spTapSoundID = 0;
	
	// BOOLEAN
	private boolean sounds = true;
	
	// LOCAL VARIABLES
	private int _playerID;
	private String _playerName, _playerAvatar;
	
	// ANIMATION
	private Animation anim;
	
	private MediaPlayer mpIdentifyImage, mpIdentifySound, mpPinPointing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         
		setContentView(R.layout.categories);
		Initialize();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//finish();
	}
	
	public void onClick(View v) {
		Bundle categoryBundle = new Bundle();
		boolean isSoundIdentification = false;
		
		switch(v.getId()){
		
			case R.id.btnImageIdentification :
				categoryBundle.putInt(Constants.KEY_CATEGORY, Constants.CATEGORY_IMAGE_IDENTIFICATION);
				Log.d("CATEGORIES", "" + Constants.CATEGORY_IMAGE_IDENTIFICATION);
				if (sounds) mpIdentifyImage.start();
				break;
				
			case R.id.btnSoundIdentification :
				categoryBundle.putInt(Constants.KEY_CATEGORY, Constants.CATEGORY_SOUND_IDENTIFICATION);
				Log.d("CATEGORIES", "" + Constants.CATEGORY_SOUND_IDENTIFICATION);
				isSoundIdentification = true;
				if (sounds) mpIdentifySound.start();
				break;
				
			case R.id.btnPinPointing :
				categoryBundle.putInt(Constants.KEY_CATEGORY, Constants.CATEGORY_IMAGE_PINPOINTING);
				Log.d("CATEGORIES", "" + Constants.CATEGORY_IMAGE_PINPOINTING);
				if (sounds) mpPinPointing.start();
				break;
				
		}
		//mpTap.start();
		if(sounds == true) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);
		
		Bundle playerBundle = new Bundle();
		playerBundle.putInt(Constants.KEY_PLAYER_ID,_playerID);
		playerBundle.putString(Constants.KEY_PLAYER_NAME, _playerName);
		playerBundle.putString(Constants.KEY_PLAYER_AVATAR, _playerAvatar);

		subCatIntent.putExtras(categoryBundle);
		subCatIntent.putExtras(playerBundle);
		startActivity(subCatIntent);

		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	private void Initialize(){
		_playerID = getIntent().getExtras().getInt(Constants.KEY_PLAYER_ID);
		_playerName = getIntent().getExtras().getString(Constants.KEY_PLAYER_NAME);
		_playerAvatar = getIntent().getExtras().getString(Constants.KEY_PLAYER_AVATAR);
		
		// INTENTS
		subCatIntent = new Intent(this, SubCategory.class);
		gameIntent = new Intent(this, Game.class);
		
		//BUTTONS
		btnImageIdentification = (Button) findViewById(R.id.btnImageIdentification);
		btnPinPointing = (Button) findViewById(R.id.btnPinPointing);
		btnSoundIdentification = (Button) findViewById(R.id.btnSoundIdentification);
		
		mpIdentifyImage = MediaPlayer.create(this, R.raw.z_identify_image);
		mpIdentifySound = MediaPlayer.create(this, R.raw.z_identify_sounds);
		mpPinPointing = MediaPlayer.create(this, R.raw.z_pin_piont);

		// SET BUTTONS's ONCLICKLISTENERS
		Button[] buttons = {btnImageIdentification, btnPinPointing, btnSoundIdentification};
		for (Button b : buttons){
        	b.setOnClickListener(this);
        	b.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));
        }
		
		anim = new TranslateAnimation(0.0f, 0.0f, -200, 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));
		anim.setStartOffset(300);
		
		//AnimateButtons();
		
		spTap = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spTapSoundID = spTap.load(this, R.raw.tap, 0);
		
		// PREFERENCES
		SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sounds = (prefs.getBoolean(Constants.KEY_PREFS_SOUND, true) == false) ? false : true;
	}
	
	private void AnimateButtons(){
		Button[] buttons = { btnImageIdentification, btnPinPointing, btnSoundIdentification};
		for (Button b : buttons) {
			b.startAnimation(anim);
		}
	}
}
