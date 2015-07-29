package br.com.sildu.selfie;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.sildu.selfie.adapter.SelfiesListAdapter;
import br.com.sildu.selfie.database.SelfiesDataBaseHelper;
import br.com.sildu.selfie.notification.AlarmNotificationReceiver;
import br.com.sildu.selfie.pojo.Selfie;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends ListActivity {

	public static final String		SELFIE				= "selfie";

	private static final int		CAMERA_REQUEST		= 100;

	private SelfiesListAdapter		selfieAdapter;
	private SelfiesDataBaseHelper	helper;

	private AlarmManager			alarmManager;
	private PendingIntent			notificationReceiverPendingIntent;
	private Intent					notificationReceiverIntent;

	private static final long		INITIAL_ALARM_DELAY	= 2 * 60 * 1000L;

	private SimpleDateFormat		sdf					= new SimpleDateFormat("yyyyMMdd_HHmmss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		selfieAdapter = new SelfiesListAdapter(this.getApplicationContext());
		this.setListAdapter(selfieAdapter);

		helper = new SelfiesDataBaseHelper(this.getApplicationContext());

		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		notificationReceiverIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);
		notificationReceiverPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, notificationReceiverIntent, 0);

		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY, INITIAL_ALARM_DELAY, notificationReceiverPendingIntent);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Read all Selfies from DB
		List<Selfie> selfies = helper.SelectAllSelfies();
		selfieAdapter.setSelfies(selfies);

		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);

				Selfie selfie = (Selfie) selfieAdapter.getItem(position);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				selfie.getSelfie().compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte[] byteArray = stream.toByteArray();

				intent.putExtra(SELFIE, byteArray);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.action_camera:
			this.callCameraIntent();
			return true;
		case R.id.action_clear:
			helper.clearAll();
			selfieAdapter.removeAllSelfies();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void callCameraIntent() {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CAMERA_REQUEST:

			if (null != data.getExtras()) {
				Object dataObject = data.getExtras().get("data");
				Bitmap photo = (Bitmap) dataObject;

				Selfie selfie = new Selfie();
				selfie.setName(sdf.format(new Date()));
				selfie.setSelfie(photo);

				selfieAdapter.addSelfie(selfie);

				// Insert Selfie in DB
				helper.insertSelfieInBD(selfie);
			}

			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}

}
