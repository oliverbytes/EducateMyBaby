package com.educate.nemory;

import java.io.IOException;

import com.educate.nemory.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnClickListener {

	// COORDINATES
	private int xFrom, xTo, yFrom, yTo;

	// BUTTON VIEWS
	private Button btnA, btnB, btnC, btnMainMenu, btnCategoriesMenu, btnShareScore;

	// TEXT VIEWS
	private TextView tvQuestion, tvScore, tvTimer, tvPoints, tvMessage;

	// IMAGE VIEWS
	@SuppressWarnings("unused")
	private ImageView ivImage, ivMessage;

	// DATABASE OBJECT
	private Database db = new Database(this);

	private String questions[] = new String[10];

	// COUNTDOWN TIMER OBJECT
	private CountDownTimer timer;

	// LOCAL SCOPE VARIABLES
	private int _category = 0, _playerScore = 0, _playerID, _subCategory, _questionTimer, _questionID, _questionPoints;
	private String _playerName, _question, _questionAnswer, _answerChoice1, _answerChoice2, _answerChoice3, _image, _sound;

	// MEDIA SOUND OBJECTS
	//private MediaPlayer mpTimeRunningOut;

	// SOUNDPOOL OBJECTS
	private SoundPool spCorrect, spWrong, spTick;

	// SOUNDPOOL IDs
	private int spCorrectSoundID = 0, spWrongSoundID = 0, spTickSoundID = 0;

	// LAYOUT
	private LinearLayout llButtons;
	
	// INTENTS
	private Intent mainMenuIntent, categoriesIntent;

	// BOOLEANS
	private boolean sounds = true, isPinPointing = false;

	// DIALOG
	private Dialog dialog;
	
	// ANIMATION
	Animation shake;
	
	// CUSTOM TOAST COMPONENTS
	LayoutInflater inflater;
	View layout;
	Toast toast;
	ImageView ivToastIcon;
	TextView tvToastMessage;
	
	MediaPlayer player;

	// ANIMATION
	private Animation anim;
	
	private Vibrator v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// FULLSCREEN THE APP
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game);
		Initialize();
		
		ivImage.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				tvQuestion.setText("X: " + e.getX() + "\n" + "Y: " + e.getY()); // for debugging mode only
				timer.cancel();
				if (e.getX() >= xFrom && e.getX() <= xTo
						&& e.getY() >= yFrom && e.getY() <= yTo) {
					Button btnTemp = new Button(Game.this);
					btnTemp.setText("force it to correct");
					compareAnswer(btnTemp, "force it to correct");
				} else {
					compareAnswer(btnA, "force it to wrong");
				}
				return false;
			}
		});
		BindViews();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
	    super.onRestoreInstanceState(savedInstanceState);
	    onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//if (sounds) mpTimeRunningOut.stop();
		if(!(timer == null)){
			timer.cancel();
			timer = null;
		}
		finish(); // kill this activity
		Log.d("GAME", "STOPPED");
	}

	@Override
	protected void onPause() {
		super.onPause();
		//if (sounds) mpTimeRunningOut.stop();
		if (sounds) player.stop();
		if(!(timer == null)){
			timer.cancel();
			timer = null;
		}
		finish(); // kill this activity
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		//if (sounds) mpTimeRunningOut.start(); // replay
	}

	private void BindViews() {
		db.open();
		questions = db.GetQuestionsData(_category, _subCategory);
		int answeredQuestionsCount = db.GetAnsweredQuestionsCount(_category, _subCategory);
		Log.d("GAME", "PLAYER: " + _playerName + "   CAT: " + _category + "   DIFF: " + _subCategory + "    COUNT: " + answeredQuestionsCount);
		db.close();
		
		// IF (questionData != null) or ALL QUESTIONS STATUS = STATUS_UNANSWERED
		if (questions != null && answeredQuestionsCount <= Constants.QUESTIONS_LIMIT) {
			
			// INITIALIZE LOCAL SCOPE VARIABLES
			_question = questions[0];
			_answerChoice1 = questions[1];
			_answerChoice2 = questions[2];
			_answerChoice3 = questions[3];
			_questionAnswer = questions[4];
			_questionID = Integer.parseInt(questions[5]);
			_questionPoints = Integer.parseInt(questions[6]);
			_questionTimer = Integer.parseInt(questions[7]);
			_image = questions[8];
			_sound = questions[9];
			
			setTimer(_questionTimer);
			
			if (_category == Constants.CATEGORY_IMAGE_PINPOINTING) {
				btnA.setVisibility(View.GONE);
				btnB.setVisibility(View.GONE);
				btnC.setVisibility(View.GONE);
				setPinPointing();
				ivImage.startAnimation(anim);
				isPinPointing = true;
				
			} else if (_category == Constants.CATEGORY_SOUND_IDENTIFICATION) {
				ivImage.setEnabled(false);
				ivImage.setImageResource(R.drawable.identify_sound);
				
				AssetFileDescriptor afd;
				try {
					Log.d("GAME", _sound);
					afd = getAssets().openFd(_sound);
					if (sounds) player.reset();
					if (sounds) player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
					if (sounds) player.setLooping(true);
					if (sounds) player.prepare();
				    if (sounds)  player.start();
				    
				} catch (IOException e) {
					e.printStackTrace();
				}

				tvQuestion.setText(_question);
				btnA.setText(_answerChoice1);
				btnB.setText(_answerChoice2);
				btnC.setText(_answerChoice3);
				
			}else if (_category == Constants.CATEGORY_IMAGE_IDENTIFICATION) {
				ivImage.setEnabled(false);
				try {
					Bitmap bitmap;
					bitmap = BitmapFactory.decodeStream(getAssets()
							.open("images/" + _image));
					ivImage.setImageBitmap(bitmap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ivImage.startAnimation(anim);
				
				tvQuestion.setText(_question);
				btnA.setText(_answerChoice1);
				btnB.setText(_answerChoice2);
				btnC.setText(_answerChoice3);
			}
			tvPoints.setText(_questionPoints + " Points");
			
		} else {
			Log.d("GAME", "question limit reached");
			timer = null;
			tvTimer.setVisibility(View.GONE);
			tvQuestion.setVisibility(View.GONE);
			tvScore.setVisibility(View.GONE);
			tvPoints.setVisibility(View.GONE);
			llButtons.setVisibility(View.GONE);
			
			// STOP TIME RUNNING OUT SOUND
			//if(sounds) mpTimeRunningOut.stop();
			if (sounds) player.stop();
			
			db.open();
			if(db.IsPlayerInScoreBoard(_playerID)){
				if(_playerScore > db.GetPlayerHighScore(_playerID)){
					db.UpdatePlayerScoreInScoreBoard(_playerID, _playerScore);
					Log.d("GAME:", "updated new score\n\nold score:" + _playerScore + "\n\n new score: " + db.GetPlayerHighScore(_playerID));
				}
			}else{
				db.AddPlayerInScoreBoard(_playerID, _playerName, _playerScore);
				Log.d("GAME:", "player:" + _playerName + " added to score board");
			}
			tvMessage.setText("Score: " + db.GetPlayerScore(_playerID) + "\nThanks for playing this category.\n" + 
					db.GetCorrectlyAnsweredCount(_category, _subCategory) + "/" + db.GetQuestionsCount(_category, _subCategory) + " Questions Answered Correctly");
			db.close();
			dialog.setTitle("Game Finished!");
			//ivMessage.setImageResource(R.drawable.splash1); // uncomment to show a custom image when game is finished
			dialog.show();
		}
	}
	
	private void setPinPointing(){
		Log.d("SET PINPOINTING", _image);
		try {
			Bitmap bitmap;
			bitmap = BitmapFactory.decodeStream(getAssets()
					.open("images/" + _image));
			ivImage.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// SPLIT COORDINATES BY COMMA ' , '
		String splittedCoords[] = _questionAnswer.split(","); // coordinates temporary stored in _questionAnswer from database
		xFrom = Integer.parseInt(splittedCoords[0]);
		xTo   = Integer.parseInt(splittedCoords[1]);
		yFrom = Integer.parseInt(splittedCoords[2]);
		yTo   = Integer.parseInt(splittedCoords[3]);
		tvQuestion.setText(_question);
	}

	private void setTimer(int seconds) {
		//if(sounds) mpTimeRunningOut.start();
		timer = new CountDownTimer(seconds, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				tvTimer.setText(((millisUntilFinished / 1000) - 1) + " Secs Left");
				if (_category != Constants.CATEGORY_SOUND_IDENTIFICATION){
					if(sounds) spTick.play(spTickSoundID, 1, 1, 0, 0, 1);
				}	
			}

			@Override
			public void onFinish() {
				if (questions != null) {

					btnA.startAnimation(shake);
					btnB.startAnimation(shake);
					btnC.startAnimation(shake);
					ivImage.startAnimation(shake);
					
					if (sounds) spWrong.play(spWrongSoundID, 1, 1, 0, 0, 1);
					db.open();
					db.UpdateQuestionStatus(_questionID, Constants.STATUS_ANSWERED, Constants.INCORRECTED);
					db.close();
					BindViews(); // RE BIND
					Log.d("GAME", "Timer Finished");
					v.vibrate(200);
				} else {
					timer = null;
					//mpTimeRunningOut.stop();
					//mpTimeRunningOut.release();
					Log.d("GAME", "Timer Finished No More Questions");
					v.vibrate(200);
				}
			}
		};
		timer.start(); // START THE TIMER
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnA:
			timer.cancel();
			compareAnswer(btnA, _questionAnswer);
			break;

		case R.id.btnB:
			timer.cancel();
			compareAnswer(btnB, _questionAnswer);
			break;

		case R.id.btnC:
			timer.cancel();
			compareAnswer(btnC, _questionAnswer);
			break;
			
		case R.id.btnMainMenu:
			dialog.dismiss();
			startActivity(mainMenuIntent); // back to main menu 
			finish(); // kill activity
			break;

		case R.id.btnCategoriesMenu:
			dialog.dismiss();
			Bundle playerBundle = new Bundle();
		    playerBundle.putInt(Constants.KEY_PLAYER_ID, _playerID);
		    playerBundle.putString(Constants.KEY_PLAYER_NAME, _playerName);
		    categoriesIntent.putExtras(playerBundle);
			startActivity(categoriesIntent); // back to categories menu
			finish(); // kill activity
			break;

		case R.id.btnShareScore:
			dialog.dismiss(); // kill dialog
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain"); 
			db.open();
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Player: "+ db.GetPlayerName(_playerID)
					+ " just got the score of " + db.GetPlayerScore(_playerID));
			db.close();
			startActivity(Intent.createChooser(sharingIntent, "Share using"));
			finish(); // kill activity
			break;
		}
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	public void compareAnswer(final Button btnAnswer, String answer2) {
		if (btnAnswer.getText().toString().equals(answer2)) { // IF ANSWER IS CORRECT
			//ShowToast(R.drawable.correct, "Congratulations! Correct Answer!\nSound: " + _sound);
			if (sounds) spCorrect.play(spCorrectSoundID, 1, 1, 0, 0, 1);
			
			/*Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
	            public void run() {*/
	    			db.open();
	    			db.UpdateQuestionStatus(_questionID, Constants.STATUS_ANSWERED, Constants.CORRECTED);
	    			db.UpdatePlayerScore(_playerID, _questionPoints, db.GetPlayerScore(_playerID));
	    			_playerScore = db.GetPlayerScore(_playerID);
	    			tvScore.setText("Score: " + db.GetPlayerScore(_playerID));
	    			db.close();

	    			BindViews(); // RE BIND
	          /*  }
	        }, 3000);*/

		} else { // IF ANSWER IS WRONG
			v.vibrate(200);
			UnRegisterButtonListeners();
			
			btnAnswer.startAnimation(shake);
			String tempCorrectAnswer = "";
			if(isPinPointing){
				tempCorrectAnswer = _answerChoice1;
			}else{
				tempCorrectAnswer = _questionAnswer;
			}
			//ShowToast(R.drawable.wrong, "Sorry!\nWrong Answer!\nCorrect Answer is: " + tempCorrectAnswer);
			if (sounds) spWrong.play(spWrongSoundID, 1, 1, 0, 0, 1);
			
			/*Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
	            public void run() {*/
	            	timer.cancel();
	    			db.open();
	    			db.UpdateQuestionStatus(_questionID, Constants.STATUS_ANSWERED, Constants.INCORRECTED);
	    			db.close();
	    			BindViews(); // RE BIND
	    			RegisterButtonListeners();
	          /*  }
	        }, 3000);*/
		}
	}

	private void Initialize() {
		/** ------------ LOCAL VARIABLES ------------- **/
		_playerID = getIntent().getExtras().getInt(Constants.KEY_PLAYER_ID);
		_playerName = getIntent().getExtras().getString(Constants.KEY_PLAYER_NAME);
		_category = getIntent().getExtras().getInt(Constants.KEY_CATEGORY);
		_subCategory = getIntent().getExtras().getInt(Constants.KEY_SUB_CAT);
		
		/** ------------ INTENTS ------------- **/
		mainMenuIntent = new Intent(this, Main.class);
		categoriesIntent = new Intent(this, Categories.class);
		
		/** ------------ LAYOUTS ------------- **/
		llButtons = (LinearLayout) findViewById(R.id.llButtons);
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		ivImage = (ImageView) findViewById(R.id.ivImage);
		
		/** ------------ CUSTOM DIALOG ------------- **/
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.customdialog);
		tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
		ivMessage = (ImageView) dialog.findViewById(R.id.ivMessage);
		btnMainMenu = (Button) dialog.findViewById(R.id.btnMainMenu);
		btnCategoriesMenu = (Button) dialog.findViewById(R.id.btnCategoriesMenu);
		btnShareScore = (Button) dialog.findViewById(R.id.btnShareScore);
		
		/** ------------ TEXTVIEWS ------------- **/
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvPoints = (TextView) findViewById(R.id.tvPoints);
		
		tvTimer.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
		tvPoints.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
		tvScore.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
		
		db.open();
		tvScore.setText("Score: " + db.GetPlayerScore(_playerID));
		db.close();
		
		/** ------------ BUTTONS ------------- **/
		btnA = (Button) findViewById(R.id.btnA);
		btnB = (Button) findViewById(R.id.btnB);
		btnC = (Button) findViewById(R.id.btnC);
		
		/** ------------ LISTENERS ------------- **/
		Button buttons[] = { btnA, btnB, btnC, btnMainMenu, btnCategoriesMenu, btnShareScore };
		for (Button b : buttons) {
			b.setOnClickListener(this);
			b.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));
		}

		/** ------------ SOUNDS / MEDIAS ------------- **/
		//mpTimeRunningOut = MediaPlayer.create(this, R.raw.time_running_out);

		spCorrect = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		spCorrectSoundID = spCorrect.load(this, R.raw.correct, 0);

		spWrong = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		spWrongSoundID = spWrong.load(this, R.raw.wrong, 0);

		spTick = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		spTickSoundID = spTick.load(this, R.raw.tick, 0);
		
		/** ------------ PREFERENCES ------------- **/
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sounds = (prefs.getBoolean(Constants.KEY_PREFS_SOUND, true) == false) ? false : true;
		
		/** ------------ ANIMATION ------------- **/
		shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		anim = new TranslateAnimation(0.0f, 0.0f, -200, 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));
		anim.setStartOffset(300);
		ivImage.startAnimation(anim);
		
		/** ------------ TOAST ------------- **/
		inflater = getLayoutInflater();
		layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.lyToastRoot));
		
		ivToastIcon = (ImageView) layout.findViewById(R.id.ivToastIcon);
		tvToastMessage = (TextView) layout.findViewById(R.id.tvToastMessage);
		
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);

		player = new MediaPlayer();
		
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void ShowToast(int imageResID, String message){
		ivToastIcon.setImageResource(imageResID);
		tvToastMessage.setText(message);
		toast.show();
	}
	
	private void RegisterButtonListeners(){
		Button buttons[] = { btnA, btnB, btnC};
		for (Button b : buttons) {
			b.setOnClickListener(this);
			b.setEnabled(true);
		}
	}
	
	private void UnRegisterButtonListeners(){
		Button buttons[] = { btnA, btnB, btnC};
		for (Button b : buttons) {
			b.setOnClickListener(null);
			b.setEnabled(false);
		}
	}
}
