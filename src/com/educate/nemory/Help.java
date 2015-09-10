package com.educate.nemory;

import java.io.IOException;
import java.io.InputStream;

import com.educate.nemory.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Help extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.help);

		TextView tvHelp = (TextView) findViewById(R.id.tvHelp);

		InputStream is;
		try {
			is = getAssets().open("help/help.txt");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			String text = new String(buffer);
			tvHelp.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
