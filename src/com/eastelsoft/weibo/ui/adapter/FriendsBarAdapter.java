package com.eastelsoft.weibo.ui.adapter;

import com.eastelsoft.weibo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendsBarAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater inflater;
	private String[] valuesArr;
	
	public FriendsBarAdapter(Context context, String[] valuesArr) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.valuesArr = valuesArr;
	}

	@Override
	public int getCount() {
		return valuesArr.length;
	}

	@Override
	public Object getItem(int position) {
		return valuesArr[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.friendsbar_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv = (TextView)convertView.findViewById(R.id.group_names);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.tv.setText(valuesArr[position]);
		
		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
			holder = new ViewHolder();
			holder.tv = (TextView)convertView.findViewById(android.R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv.setWidth(140);
		holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
		holder.tv.setText(valuesArr[position]);
		return convertView;
	}
	
	private static class ViewHolder {
		TextView tv;
	}

}
