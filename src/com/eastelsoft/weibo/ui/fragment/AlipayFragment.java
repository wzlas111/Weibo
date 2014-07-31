package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlipayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("AlipayFragment ===> onCreateView");
		View view = inflater.inflate(R.layout.fragment_alipay, container, false);
		return view;
	}
}
