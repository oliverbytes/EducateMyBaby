package com.educate.nemory;

import com.educate.nemory.R;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HighScores extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.highscore);
		
		TableLayout tblScoreBoard = (TableLayout) findViewById(R.id.tblScoreBoard);
		Database db = new Database(this);
		
		db.open();
		Cursor cursorPlayers = db.GetPlayersInScoreBoard();
		db.close();
		
		// make a style for textviews
		TextView tvHeaderPlayerName = new TextView(this);
		tvHeaderPlayerName.setTextColor(Color.WHITE);
		tvHeaderPlayerName.setBackgroundDrawable(getResources().getDrawable(R.drawable.cellheader));
		tvHeaderPlayerName.setPadding(5, 5, 5, 5);// (left, top, right, bottom)
		tvHeaderPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);// also the same as 20sp in xml
		tvHeaderPlayerName.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
		tvHeaderPlayerName.setText("\tPlayer Name\t");
		
		TextView tvHeaderPlayerScore = new TextView(this);
		tvHeaderPlayerScore.setTextColor(Color.WHITE);
		tvHeaderPlayerScore.setBackgroundDrawable(getResources().getDrawable(R.drawable.cellheader));
		tvHeaderPlayerScore.setPadding(5, 5, 5, 5);// (left, top, right, bottom)
		tvHeaderPlayerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);// also the same as 20sp in xml
		tvHeaderPlayerScore.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
		tvHeaderPlayerScore.setText("\tHigh Score\t\t");
		
		TableRow rowHeader = new TableRow(this);
		rowHeader.setGravity(Gravity.CENTER);
		rowHeader.addView(tvHeaderPlayerName);
		rowHeader.addView(tvHeaderPlayerScore);
		
		tblScoreBoard.addView(rowHeader);
		
		if(!(cursorPlayers == null)){ // IF THERE IS ATLEAST ONE PLAYER IN SCOREBOARD
			while(cursorPlayers.moveToNext()){
				Log.d("SCORE BOARD", "Players Count: " + cursorPlayers.getCount());
				TableRow newRow = new TableRow(this);
				newRow.setGravity(Gravity.CENTER);
				
				if((cursorPlayers.getPosition() % 2) == 0){
					newRow.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}else{
					newRow.setBackgroundColor(Color.parseColor("#ecf4ff"));
				}
				
				String playerName = cursorPlayers.getString(cursorPlayers.getColumnIndex(Constants.COLUMN_SCORE_BOARD_PLAYER_NAME));
				int playerHighScore = cursorPlayers.getInt(cursorPlayers.getColumnIndex(Constants.COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE));

				TextView tvPlayerName = new TextView(this);
				tvPlayerName.setTextColor(Color.GRAY);
				tvPlayerName.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell));
				tvPlayerName.setPadding(5, 5, 5, 5);// (left, top, right, bottom)
				tvPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30); // also the same as 20sp in xml
				tvPlayerName.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
				tvPlayerName.setText("\t\t" + playerName);
				Log.d("SCORE BOARD", "Players Name: " + playerName);
				
				TextView tvPlayerScore = new TextView(this);
				tvPlayerScore.setTextColor(Color.GRAY);
				tvPlayerScore.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell));
				tvPlayerScore.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_COOKIE));
				tvPlayerScore.setPadding(5, 5, 5, 5); // (left, top, right, bottom)
				tvPlayerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30); // also the same as 20sp in xml
				tvPlayerScore.setText("\t\t" + playerHighScore);
				Log.d("SCORE BOARD", "Player Score: " + playerHighScore);
				
				newRow.addView(tvPlayerName);
				newRow.addView(tvPlayerScore);
				
				tblScoreBoard.addView(newRow);
			}
		}else{ // IF THERE IS NO PLAYER IN SCOREBOARD
			Toast.makeText(this, "No one has made a high score yet.", Constants._10_SECONDS).show();
		}
		
	}
}
