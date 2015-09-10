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
import android.widget.ImageView;
import android.widget.TextView;

public class SubCategory  extends Activity implements OnClickListener{
	// INTENTS
	private Intent intentGame;
	
	// BUTTONS
	private Button btnAnimals, btnCartoons, btnMathematics, btnShapes, btnColors, btnObjects, btnBody, btnNurseryRhimes, btnObjectsInstruments;
	
	private TextView tvCategoryIn;
	private ImageView ivSubCatImage;
	
	// SOUNDPOOL OBJECTS
	private SoundPool spTap;
	
	// SOUNDPOOL IDs
	private int spTapSoundID = 0;
	
	// BOOLEAN
	private boolean sounds = true;
	
	// LOCAL VARIABLES
	private int _playerID, _category, _image;
	private String _playerName, _category_in;

	// ANIMATION
	private Animation anim;
	
	private MediaPlayer mpAnimals, mpCartoons, mpMathematics, mpShapes, mpColors, mpObjects, mpBody, mpNurseryRhimes, mpObjectsInstruments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.subcategory);
		Initialize();
		
		if(_category == Constants.CATEGORY_IMAGE_PINPOINTING){
			ivSubCatImage.setImageResource(R.drawable.identify_pinpoint);
			btnAnimals.setVisibility(View.GONE);
			btnCartoons.setVisibility(View.GONE);
			btnNurseryRhimes.setVisibility(View.GONE);
			btnObjectsInstruments.setVisibility(View.GONE);
			btnMathematics.setVisibility(View.GONE);
			btnColors.setVisibility(View.GONE);
			
		}else if(_category == Constants.CATEGORY_SOUND_IDENTIFICATION){
			ivSubCatImage.setImageResource(R.drawable.identify_sound);
			
			btnCartoons.setVisibility(View.GONE);
			btnMathematics.setVisibility(View.GONE);
			btnShapes.setVisibility(View.GONE);
			btnColors.setVisibility(View.GONE);
			btnObjects.setVisibility(View.GONE);
			btnBody.setVisibility(View.GONE);
			
		}else{
			ivSubCatImage.setImageResource(R.drawable.identify_image);
			btnNurseryRhimes.setVisibility(View.GONE);
			btnObjectsInstruments.setVisibility(View.GONE);
			btnObjects.setVisibility(View.GONE);
			btnBody.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//finish();
	}

	public void onClick(View v) {
		Bundle subCatBundle = new Bundle();
		
		switch(v.getId()){
			case R.id.btnAnimals :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_ANIMALS);
				mpAnimals.start();
				break;
				
			case R.id.btnCartoons :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_CARTOONS);
				//mpCartoons.start();
				break;
				
			case R.id.btnMathematics :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_MATHEMATICS);
				mpMathematics.start();
				break;
				
			case R.id.btnShapes :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_SHAPES);
				mpShapes.start();
				break;
				
			case R.id.btnColors :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_COLORS);
				mpColors.start();
				break;
				
			case R.id.btnObjects :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_OBJECTS);
				mpObjects.start();
				break;
				
			case R.id.btnBody :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_BODY);
				mpBody.start();
				break;
				
			case R.id.btnNurseryRhimes :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_NURSERY);
				//mpNursery.start();
				break;

			case R.id.btnObjectsInstruments :
				subCatBundle.putInt(Constants.KEY_SUB_CAT, Constants.SUB_CAT_OBJECTS_INSTRUMENTS);
				//mpObjectsInstruments.start();
				break;

		}
		//mpTap.start();
		if(sounds == true) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);

		Bundle categoryBundle = new Bundle(); 
		categoryBundle.putInt(Constants.KEY_CATEGORY, _category);
		
		Bundle playerBundle = new Bundle(); 
		playerBundle.putInt(Constants.KEY_PLAYER_ID, _playerID);
		playerBundle.putString(Constants.KEY_PLAYER_NAME, _playerName);
		
		intentGame.putExtras(subCatBundle);
		Log.d("DIFFICULTY", "cat: " + _category);
		intentGame.putExtras(categoryBundle);
		intentGame.putExtras(playerBundle);
		startActivity(intentGame);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	private void Initialize(){
		_playerID = getIntent().getExtras().getInt(Constants.KEY_PLAYER_ID);
		_playerName = getIntent().getExtras().getString(Constants.KEY_PLAYER_NAME);
		_category = getIntent().getExtras().getInt(Constants.KEY_CATEGORY);
		_category_in = getIntent().getExtras().getString(Constants.KEY_CATEGORY_IN);
		_image = getIntent().getExtras().getInt(Constants.KEY_IMAGE);
		
		// INTENTS
		intentGame = new Intent(this, Game.class);
		
		//BUTTONS
		btnAnimals = (Button) findViewById(R.id.btnAnimals);
		btnCartoons = (Button) findViewById(R.id.btnCartoons);
		btnMathematics = (Button) findViewById(R.id.btnMathematics);
		btnShapes = (Button) findViewById(R.id.btnShapes);
		btnColors = (Button) findViewById(R.id.btnColors);
		btnObjects = (Button) findViewById(R.id.btnObjects);
		btnBody = (Button) findViewById(R.id.btnBody);
		btnNurseryRhimes = (Button) findViewById(R.id.btnNurseryRhimes);
		btnObjectsInstruments = (Button) findViewById(R.id.btnObjectsInstruments);
		
		mpAnimals = MediaPlayer.create(this, R.raw.animals);
		mpCartoons = MediaPlayer.create(this, R.raw.cartoons);
		mpMathematics = MediaPlayer.create(this, R.raw.mathematics);
		mpShapes = MediaPlayer.create(this, R.raw.shapes);
		mpColors = MediaPlayer.create(this, R.raw.colors);
		mpObjects = MediaPlayer.create(this, R.raw.z_objects);
		mpBody = MediaPlayer.create(this, R.raw.z_body);
		
		//mpNurseryRhimes = MediaPlayer.create(this, R.raw.);
		//mpObjectsInstruments = MediaPlayer.create(this, R.raw.z_body);
		
		// IMAGE
		ivSubCatImage = (ImageView) findViewById(R.id.ivSubCatImage);
		
		@SuppressWarnings("unused")
		Typeface fontButton = Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON);
		// SET BUTTONS's ONCLICKLISTENERS
		
		Button[] buttons = {btnAnimals, btnCartoons, btnMathematics, btnShapes, btnColors, btnObjects, btnBody, btnNurseryRhimes, btnObjectsInstruments};
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
		Button[] buttons = {btnAnimals, btnCartoons, btnMathematics, btnShapes, btnColors, btnObjects, btnBody, btnNurseryRhimes, btnObjectsInstruments};
		for (Button b : buttons) {
			b.startAnimation(anim);
		}
	}
}
