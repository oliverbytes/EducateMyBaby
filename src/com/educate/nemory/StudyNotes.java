package com.educate.nemory;

import com.educate.nemory.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class StudyNotes extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// FULLSCREEN THE APP
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.studynotes);
		WebView wvStudyNotes = (WebView) findViewById(R.id.wvStudyNotes);
		wvStudyNotes.loadUrl("file:///android_asset/notes/notes.htm");
	}
}
