package com.eastelsoft.weibo.ui.adapter;

import java.util.List;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.TimelineBean;
import com.eastelsoft.weibo.bean.UserBean;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemAdapter extends BaseAdapter {
	
	protected Fragment mFragment;
	protected LayoutInflater mLayoutInflater;
	protected List<TimelineBean> mList;
	
	public ListItemAdapter(Fragment fragment, List<TimelineBean> pList) {
		mFragment = fragment;
		mLayoutInflater = fragment.getActivity().getLayoutInflater();
		mList = pList;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = mLayoutInflater.inflate(R.layout.listview_item_layout, parent, false);
			viewHolder = buildView(convertView);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		buildData(viewHolder, position);
		convertView.setTag(viewHolder);
		return convertView;
	}
	
	private ViewHolder buildView(View view) {
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.img_avatar = (ImageView)view.findViewById(R.id.avatar);
		viewHolder.tv_repost_count = (TextView)view.findViewById(R.id.repost_count);
		viewHolder.tv_comment_count = (TextView)view.findViewById(R.id.comment_count);
		viewHolder.tv_username = (TextView)view.findViewById(R.id.username);
		viewHolder.tv_post_time = (TextView)view.findViewById(R.id.post_time);
		viewHolder.tv_source = (TextView)view.findViewById(R.id.source);
		viewHolder.tv_content = (TextView)view.findViewById(R.id.content);
		viewHolder.img_content_pic = (ImageView)view.findViewById(R.id.content_pic);
		viewHolder.gv_content_pic_muti = (GridView)view.findViewById(R.id.content_pic_muti);
		return viewHolder;
	}
	
	private void buildData(ViewHolder viewHolder, int position) {
		TimelineBean bean = mList.get(position);
		UserBean user = bean.getUser();
		
	}
	
	private class ViewHolder {
		
		ImageView img_avatar;
		TextView tv_repost_count;
		TextView tv_comment_count;
		TextView tv_username;
		TextView tv_post_time;
		TextView tv_source;
		TextView tv_content;
		ImageView img_content_pic;
		GridView gv_content_pic_muti;
		
	}

}
