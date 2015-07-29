package br.com.sildu.selfie;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {

	private SelfiesListAdapter	selfieAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//

		selfieAdapter = new SelfiesListAdapter(this.getApplicationContext());
		this.setListAdapter(selfieAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();

		Selfie selfie = new Selfie();
		selfie.setName("TESTE");

		selfieAdapter.addSelfie(selfie);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_camera) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
