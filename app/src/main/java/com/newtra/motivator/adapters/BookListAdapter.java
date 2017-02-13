package com.newtra.motivator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.newtra.motivator.Beans.Book;
import com.newtra.motivator.R;

import java.util.ArrayList;
import java.util.List;


public class BookListAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<Book> worldpopulationlist = null;
	private ArrayList<Book> arraylist;

	public BookListAdapter(Context context, List<Book> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Book>();
		this.arraylist.addAll(worldpopulationlist);
	}

	public class ViewHolder {
		TextView rank;
		TextView country;
//		TextView population;
		Button bookCover;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	@Override
	public Book getItem(int position) {
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
			view = inflater.inflate(R.layout.row_book_layout, null);
			// Locate the TextViews in listview_item.xml
			holder.rank = (TextView) view.findViewById(R.id.store_list_item_text);
			holder.country = (TextView) view.findViewById(R.id.textViewDescription);
			holder.bookCover = (Button) view.findViewById(R.id.btnBookCover);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.rank.setText(worldpopulationlist.get(position).getTitle());
		holder.country.setText(worldpopulationlist.get(position).getDescription());
		holder.bookCover.setBackground(mContext.getResources().getDrawable(worldpopulationlist.get(position).getBookCoverId()));

		return view;
	}

}
