package com.educate.nemory;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Drums extends Activity implements OnClickListener{

	private SoundPool spSnaire, spCymbal, spBass;
	private int spSnaireID = 0, spCymbalID = 0, spBassID = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// SET FULLSCREEN MODE
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// SET LAOUT FOR THIS ACTIVITY
		setContentView(R.layout.drums);
		
		Button btnSnaire = (Button) findViewById(R.id.btnSnaire);
		Button btnBass = (Button) findViewById(R.id.btnBass);
		Button btnCymbals = (Button) findViewById(R.id.btnCymbals);
		
		// SOUNDPOOL SOUNDS
		spSnaire = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spSnaireID = spSnaire.load(this, R.raw.d_snaire, 0);
		
		spBass = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spBassID = spBass.load(this, R.raw.d_bass, 0);
		
		spCymbal = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spCymbalID = spCymbal.load(this, R.raw.d_cymbal, 0);
		
		Button[] btnArray = {btnBass, btnCymbals, btnSnaire};
		
		for(Button b : btnArray){
			b.setOnClickListener(this);
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSnaire :
			spSnaire.play(spSnaireID, 1, 1, 0, 0, 1);
			break;
			
		case R.id.btnBass :
			spBass.play(spBassID, 1, 1, 0, 0, 1);
			break;
			
		case R.id.btnCymbals :
			spCymbal.play(spCymbalID, 1, 1, 0, 0, 1);
			break;
		}
	}

}
