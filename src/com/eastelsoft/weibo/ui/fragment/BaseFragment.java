package com.eastelsoft.weibo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private boolean isFirstStartFlag = true;

	protected final static int FIRST_TIME_START = 0;
	protected final static int SCREEN_RETATE = 1;
	protected final static int DESTROY_AND_CREATE = 2;
	
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
