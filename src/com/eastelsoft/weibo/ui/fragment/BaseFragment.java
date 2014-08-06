package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BaseFragment extends Fragment {

	private boolean isFirstStartFlag = true;

	protected final static int FIRST_TIME_START = 0;
	protected final static int SCREEN_RETATE = 1;
	protected final static int DESTROY_AND_CREATE = 2;
	
	protected PullToRefreshListView pullToRefreshListView;
	protected View footerView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listview_layout, container, false);
		buildLayout(inflater,view);
		return view;
	}
	
	private void buildLayout(LayoutInflater inflater,View view) {
		pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
	
		footerView = inflater.inflate(R.layout.listview_footer_layout, null);
		getListView().addFooterView(footerView);
		dismissFooterView();
	}
	
	public ListView getListView() {
		return pullToRefreshListView.getRefreshableView();
	}
	
	public void dismissFooterView() {
		footerView.findViewById(R.id.loading_progressBar).setVisibility(View.GONE);
		footerView.findViewById(R.id.loading_fail).setVisibility(View.GONE);
	}
	
	public int getCurrentState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			isFirstStartFlag = false;
			return DESTROY_AND_CREATE;
		}
		
		if (!isFirstStartFlag) {
			return SCREEN_RETATE;
		}
		
		isFirstStartFlag = false;
		return FIRST_TIME_START;
	}
}
