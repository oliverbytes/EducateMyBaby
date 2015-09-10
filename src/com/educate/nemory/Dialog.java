package com.educate.nemory;

import com.educate.nemory.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Dialog extends Activity{
	
	public static TextView tvMessage;
	public static ImageView ivMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		ivMessage = (ImageView) findViewById(R.id.ivMessage);
	}
}
