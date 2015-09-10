package com.educate.nemory;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class Xylophone extends Activity implements OnTouchListener{
	
	private Button btnDo1, btnRe, btnMi, btnFa, btnSo, btnLa, btnTi, btnDo2;
	private SoundPool spDo1, spRe, spMi, spFa, spSo, spLa, spTi, spDo2;
	private int spDo1ID, spReID, spMiID, spFaID, spSoID, spLaID, spTiID, spDo2ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xylophone);
		Initialize();
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch(v.getId()){
		case R.id.btnDo1:
			spDo1.play(spDo1ID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnRe:
			spRe.play(spReID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnMi:
			spMi.play(spMiID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnFa:
			spFa.play(spFaID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnSo:
			spSo.play(spSoID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnLa:
			spLa.play(spLaID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnTi:
			spTi.play(spTiID, 1, 1, 0, 0, 1);
			break;
		case R.id.btnDo2:
			spDo2.play(spDo2ID, 1, 1, 0, 0, 1);
			break;
		}
		return false;
	}
	
	private void Initialize(){
		spDo1 = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spDo1ID = spDo1.load(this, R.raw.x_do, 0);
		
		spRe = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spReID = spRe.load(this, R.raw.x_re, 0);
		
		spMi = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spMiID = spMi.load(this, R.raw.x_mi, 0);
		
		spFa = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spFaID = spFa.load(this, R.raw.x_fa, 0);
		
		spSo = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spSoID = spSo.load(this, R.raw.x_so, 0);
		
		spLa = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spLaID = spLa.load(this, R.raw.x_la, 0);
		
		spTi = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spTiID = spTi.load(this, R.raw.x_ti, 0);
		
		spDo2 = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		spDo2ID = spDo2.load(this, R.raw.x_do2, 0);
		
		 btnDo1 = (Button) findViewById(R.id.btnDo1);
		 btnRe = (Button) findViewById(R.id.btnRe);
		 btnMi = (Button) findViewById(R.id.btnMi);
		 btnFa = (Button) findViewById(R.id.btnFa);
		 btnSo = (Button) findViewById(R.id.btnSo);
		 btnLa = (Button) findViewById(R.id.btnLa);
		 btnTi = (Button) findViewById(R.id.btnTi);
		 btnDo2 = (Button) findViewById(R.id.btnDo2);
		
		Button[] buttons = { btnDo1, btnRe, btnMi, btnFa, btnSo, btnLa, btnTi, btnDo2};
		for (Button b : buttons) {
			b.setOnTouchListener(this);
			b.setTypeface(Typeface.createFromAsset(getAssets(), Constants.FONT_BUTTON));
		}
	}

}
