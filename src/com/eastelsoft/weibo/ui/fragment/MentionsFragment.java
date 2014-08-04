package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MentionsFragment extends BaseFragment {

	public MentionsFragment() {}
	
	public static MentionsFragment newInstance() {
		MentionsFragment fragment = new MentionsFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mentions_fragment, container, false);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	public void buildActionBar() {
		getActivity().getActionBar().setTitle(getString(R.string.t_metions));
		getActivity().getActionBar().setIcon(R.drawable.repost_light);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
