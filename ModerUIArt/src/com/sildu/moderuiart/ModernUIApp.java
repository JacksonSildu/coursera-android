package com.sildu.moderuiart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class ModernUIApp extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modern_uiapp);

		Display display = getWindowManager().getDefaultDisplay();

		View view = getLayoutInflater().inflate(R.layout.activity_modern_uiapp, null);
		RelativeLayout mainLayout = (RelativeLayout) view.findViewById(R.id.main_view);
		LinearLayout leftLayout = (LinearLayout) view.findViewById(R.id.left_view);

		Point size = new Point();
		display.getSize(size);

		int width = size.x;
		int height = size.y;

		Rect rect = new Rect(this, new float[] { width / 3, height / 2, width / 3, height / 2 }, Color.RED);
		mainLayout.addView(rect);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modern_uiapp, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.more_informations:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
