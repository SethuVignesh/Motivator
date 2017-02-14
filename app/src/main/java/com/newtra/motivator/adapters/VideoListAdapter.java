package com.newtra.motivator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.newtra.motivator.beanobject.VideoList;
import com.newtra.motivator.R;

import java.util.ArrayList;
import java.util.List;


public class VideoListAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<VideoList> worldpopulationlist = null;
	private ArrayList<VideoList> arraylist;

	public VideoListAdapter(Context context, List<VideoList> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<VideoList>();
		this.arraylist.addAll(worldpopulationlist);
	}

	public class ViewHolder {

		TextView language;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	@Override
	public VideoList getItem(int position) {
		return worldpopulationlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.language_list, null);

			holder.language = (TextView) view.findViewById(R.id.btnLanguage);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.language.setText(worldpopulationlist.get(position).getFont());
		return view;
	}



}
