package com.educate.nemory;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class Constants{
	
	public void showDialog(String TITLE, String MESSAGE, Context context){
        Dialog msg = new Dialog(context);
        msg.setTitle(TITLE);
        TextView tvMsg = new TextView(context);
        tvMsg.setText(MESSAGE);
        msg.setContentView(tvMsg);
        msg.show();
	}
	
	public void showDialog(String TITLE, String MESSAGE, View v, Context context){
        Dialog msg = new Dialog(context);
        msg.setTitle(TITLE);
        msg.setContentView(v);
        msg.show();
	}
	
	//DATABASE VARIABLES
	public static final String DATABASE_NAME = "dbEducateMyBaby";
	public static final int	   DATABASE_VERSION = 1;
	
	//TABLES
	public static final String TABLE_QUESTIONS = "tblQuestion";
	public static final String TABLE_PLAYERS = "tblPlayers";
	public static final String TABLE_SCORE_BOARD = "tblScoreBoard";
    
	//QUESTION COLUMNS
	public static final String COLUMN_QUESTION_ID = "QuestionID";
	public static final String COLUMN_QUESTION = "Question";
	public static final String COLUMN_QUESTION_CATEGORY = "Category";
	public static final String COLUMN_QUESTION_SUB_CATEGORY = "SubCategory";
	public static final String COLUMN_QUESTION_STATUS = "Status";
	public static final String COLUMN_QUESTION_ANSWER = "CorrectAnswer";
	public static final String COLUMN_QUESTION_A = "A";
	public static final String COLUMN_QUESTION_B = "B";
	public static final String COLUMN_QUESTION_C = "C";
	public static final String COLUMN_QUESTION_IMAGE = "Image";
	public static final String COLUMN_QUESTION_SCORE_POINTS = "ScorePoints";
	public static final String COLUMN_QUESTION_TIMER_SECS = "TimerSecs";
	public static final String COLUMN_QUESTION_SOUND = "Sound";
	public static final String COLUMN_QUESTION_CORRECTED = "Corrected";
    
	//PLAYER COLUMNS
	public static final String COLUMN_PLAYER_ID = "PlayerID";
	public static final String COLUMN_PLAYER_NAME = "PlayerName";
	public static final String COLUMN_PLAYER_SCORE = "PlayerScore";
	public static final String COLUMN_PLAYER_AVATAR = "Avatar";
	
	//SCORE BOARD COLUMNS
	public static final String COLUMN_SCORE_BOARD_PLAYER_ID = "PlayerID";
	public static final String COLUMN_SCORE_BOARD_PLAYER_NAME = "PlayerName";
	public static final String COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE = "PlayerHighScore";
	
	//CATEGORIES INTEGER
	public static final int CATEGORY_IMAGE_IDENTIFICATION = 1;
	public static final int CATEGORY_IMAGE_PINPOINTING= 2;
	public static final int CATEGORY_SOUND_IDENTIFICATION = 3;
	
	public static final int CATEGORY_STUDY_NOTES = 4;
	
	//DIFFICULTY INTEGER
	public static final int	SUB_CAT_ANIMALS = 1;
	public static final int	SUB_CAT_CARTOONS = 2;
	public static final int	SUB_CAT_MATHEMATICS = 3;
	public static final int	SUB_CAT_SHAPES = 4;
	public static final int	SUB_CAT_COLORS = 5;
	public static final int	SUB_CAT_OBJECTS = 6;
	public static final int	SUB_CAT_BODY = 7;
	public static final int	SUB_CAT_NURSERY = 8;
	public static final int	SUB_CAT_OBJECTS_INSTRUMENTS = 9;

	//STATUS
	public static final int	STATUS_ANSWERED = 1;
	public static final int	STATUS_UNANSWERED = 0;
	
	//CORRECTED
	public static final int	CORRECTED = 1;
	public static final int	INCORRECTED = 0;
	
	//KEYS
	public static final String	KEY_CATEGORY = "key_category";
	public static final String	KEY_SUB_CAT= "key_subcat";
	public static final String	KEY_CATEGORY_IN= "key_category_in";
	public static final String	KEY_PLAYER_ID = "key_player_id";
	public static final String	KEY_PLAYER_NAME = "key_player_name";
	public static final String	KEY_PLAYER_AVATAR = "key_player_avatar";
	public static final String	KEY_IMAGE = "key_image";
	
	// PREFS KEYS
	public static final String	KEY_PREFS_SOUND = "key_prefs_sounds";
	
	//FONTS
	public static final String	FONT_BUTTON = "fonts/font_button.ttf";
	public static final String	FONT_TIMER = "fonts/digital_clock.ttf";
	public static final String	FONT_CHALK = "fonts/Chalk.ttf";
	public static final String	FONT_COOKIE = "fonts/cookies.ttf";
	
	// BOOLEAN FLAGS
	public static final boolean	PLAYER_EXIST = true;
	public static final boolean	PLAYER_DOES_NOT_EXIST = false;
	
	// SECONDS CONSTANT
	public static final int	_10_SECONDS = 10000;
	public static final int	_20_SECONDS = 20000;
	public static final int	_30_SECONDS = 30000;
	
	// QUESTIONS LIMIT PER GAME
	public static final int	QUESTIONS_LIMIT = 15;
	
}
