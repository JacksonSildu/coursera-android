package br.com.sildu.selfie.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.sildu.selfie.R;
import br.com.sildu.selfie.pojo.Selfie;

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
			newView = inflater.inflate(R.layout.selfie_badge_view, parent, false);

			holder = newViewHolder(newView);
		} else {
			if (newView.getTag() != null) {
				holder = (ViewHolder) newView.getTag();
			} else {
				holder = newViewHolder(newView);
			}

		}

		holder.name.setText(selfie.getName());
		if (selfie.getSelfie() != null) {
			holder.selfie.setImageBitmap(selfie.getSelfie());
		}

		return newView;
	}

	private ViewHolder newViewHolder(View newView) {
		ViewHolder holder;
		holder = new ViewHolder();
		if (newView.findViewById(R.id.selfie) != null) {
			holder.selfie = (ImageView) newView.findViewById(R.id.selfie);
		}

		holder.name = (TextView) newView.findViewById(R.id.name);
		return holder;
	}

	static class ViewHolder {
		ImageView	selfie;
		TextView	name;
	}

	public void addSelfie(Selfie selfie) {
		selfies.add(selfie);
		notifyDataSetChanged();
	}

	public List<Selfie> getSelfies() {
		return selfies;
	}

	public void setSelfies(List<Selfie> selfies) {
		this.selfies = selfies;
		notifyDataSetChanged();
	}

	public void removeAllSelfies() {
		selfies.clear();
		notifyDataSetChanged();
	}
}
