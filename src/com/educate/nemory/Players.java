package com.educate.nemory;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.educate.nemory.R;

public class Players extends Activity{
	
	// DATABASE OBJECT
	private Database db = new Database(this); 
	
	// LAYOUT
	@SuppressWarnings("unused")
	private LinearLayout lyPlayers;
	
	// BUTTON VIEW OBJECT
	private Button btnAdd;
	
	//EDITTEXT VIEW OBJECT
	private EditText etPlayerName;
	
	// LIST VIEW OBJECT
	private ListView listPlayers;
	
	// SOUNDPOOL OBJECTS
	private SoundPool spTap;
	
	// SOUNDPOOL IDs
	private int spTapSoundID = 0;
	
	// SOUNDS FLAGGER
	private boolean sounds = true;
	
	// LISTVIEW ITEM TEXT HOLDER
	private String playerName = null;
	
	// HOLDER FOR ALL PLAYERS
	private ArrayList<String> listItems = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	private MediaPlayer mpPlayerAdded, mpPlayerDeleted, mpPlayerSelected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.players);
		Initialize();
		BindPlayers();
		
		Animation anim = new TranslateAnimation(0.0f, 0.0f, -200, 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));
		anim.setStartOffset(300);
        
        //btnAdd.startAnimation(anim);
        //etPlayerName.startAnimation(anim);
        //listPlayers.startAnimation(anim);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//finish(); // kill activity
	}

	private void Initialize(){
		listPlayers = (ListView) findViewById(R.id.listPlayers);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		etPlayerName = (EditText) findViewById(R.id.etPlayerName);

		spTap = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spTapSoundID = spTap.load(this, R.raw.tap, 0);

		 mpPlayerDeleted = MediaPlayer.create(this, R.raw.z_players_deleted);
		 mpPlayerAdded = MediaPlayer.create(this, R.raw.z_players_added);
		 mpPlayerSelected = MediaPlayer.create(this, R.raw.z_player_select);
		
		// PREFERENCES
		SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		// IF TRUE : SOUNDS IS ON; IF FALSE : OFF
		sounds = (prefs.getBoolean(Constants.KEY_PREFS_SOUND, true)) ? true : false;
		
		// LISTENERS
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddPlayer();
				if(sounds) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);
			}
		});
		
		btnAdd.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));
		
		listPlayers.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				   if(sounds) spTap.play(spTapSoundID, 1, 1, 0, 0, 1);
				   mpPlayerSelected.start();
				   
				   db.open();
				   db.ResetQuestions();
				   db.ResetPlayersScore();
				   int playerID = db.GetPlayerIDByName(listPlayers.getItemAtPosition(position).toString());
				   String playerName = db.GetPlayerName(playerID);
				   db.close();
				   
				   Bundle playerBundle = new Bundle();
				   playerBundle.putInt(Constants.KEY_PLAYER_ID, playerID);
				   playerBundle.putString(Constants.KEY_PLAYER_NAME, playerName);
				   Intent categoriesIntent = new Intent(Players.this, Categories.class);
				   categoriesIntent.putExtras(playerBundle);
				   startActivity(categoriesIntent);
				   overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			   }
			});

		etPlayerName.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER) AddPlayer();
				return false;
			}
		});
		
		listPlayers.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {		        
		        menu.add("Delete").setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						db.open();
						db.DeletePlayer(db.GetPlayerIDByName(playerName));
						db.close();
						BindPlayers();
						Toast.makeText(Players.this, "Player: " + playerName + " Successfully Deleted.", 10000).show();
						if (sounds) mpPlayerDeleted.start();
						return false;
					}
				});
		        
		        menu.add("Delete All").setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						db.open();
						db.DeleteAllPlayers();
						db.close();
						BindPlayers();
						Toast.makeText(Players.this, "All Successfully Deleted.", 300).show();
						if (sounds) mpPlayerDeleted.start();
						return false;
					}
				});
			}
		});
		
		listPlayers.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				playerName = (String) listPlayers.getItemAtPosition(position);
		        return false;
		    }
		});
	}
	
	public void BindPlayers(){
		db.open();
		int playersCount = db.GetPlayersCount();
		db.close();
		
		if(playersCount > 0){
			ArrayList<String> playerList;
			db.open();
			playerList = db.GetPlayers();
			db.close();
			
			listItems.clear();
			for(String p : playerList){
				listItems.add(p);
			}
			
			adapter = new ArrayAdapter<String>(this, R.layout.listviewstyle, listItems);
			listPlayers.setAdapter(adapter);
		}else{
			listItems.clear();
			adapter = new ArrayAdapter<String>(this, R.layout.listviewstyle, listItems);
			listPlayers.setAdapter(adapter);
		}
	}
	
	private void AddPlayer(){
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		
		if(etPlayerName.getText().toString().length() > 1){ // atleast two letters
			db.open();
			if(db.thisPlayerExists(etPlayerName.getText().toString()) == Constants.PLAYER_DOES_NOT_EXIST){
				db.AddPlayer(etPlayerName.getText().toString());
				etPlayerName.setText(""); // clear player name text field after adding a player
				if (sounds) mpPlayerAdded.start();
			}else{
			     etPlayerName.startAnimation(shake);
			     Toast.makeText(this, "Name already taken.", 2000).show();
			   //if (sounds) mpPlayerAlreadyExist.start();
			}
			db.close();
			BindPlayers(); // re-bind players in listview
		}else{
		     etPlayerName.startAnimation(shake);
		     Toast.makeText(this, "Your name must be atleast 2 characters", 2000).show();
		}
	}
}
