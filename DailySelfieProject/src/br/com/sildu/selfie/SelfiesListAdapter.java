package br.com.sildu.selfie;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelfiesListAdapter extends BaseAdapter {

	private List<Selfie>	selfies		= new ArrayList<Selfie>();
	private LayoutInflater	inflater	= null;
	private Context			context;

	public SelfiesListAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return selfies.size();
	}

	@Override
	public Object getItem(int position) {
		return selfies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View newView = convertView;
		ViewHolder holder;

		Selfie selfie = selfies.get(position);

		if (null == convertView) {
			holder = new ViewHolder();
			newView = inflater.inflate(R.layout.selfie_badge_view, parent, false);

			if (newView.findViewById(R.id.selfie) != null) {
				holder.selfie = (ImageView) newView.findViewById(R.id.selfie);
			}

			holder.name = (TextView) newView.findViewById(R.id.name);
		} else {
			holder = (ViewHolder) newView.getTag();
		}

		holder.selfie.setImageBitmap(selfie.getSelfie());
		holder.name.setText(selfie.getName());

		return newView;
	}

	static class ViewHolder {

		ImageView	selfie;
		TextView	name;
	}

	public void addSelfie(Selfie selfie) {
		selfies.add(selfie);
	}

	public List<Selfie> getSelfies() {
		return selfies;
	}

	public void removeAllSelfies() {
		selfies.clear();
	}
}
