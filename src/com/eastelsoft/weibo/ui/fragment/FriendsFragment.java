package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;

import android.annotation.SuppressLint;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class FriendsFragment extends BaseFragment {
	
	private AccountBean accountBean;
	private UserBean userBean;
	private String token;
	
	public FriendsFragment() {}
	
	public static FriendsFragment newInstance(AccountBean accountBean, UserBean userBean, String token) {
		FriendsFragment fragment = new FriendsFragment(accountBean, userBean, token);
		fragment.setArguments(new Bundle());
		return fragment;
	}
	
	public FriendsFragment(AccountBean accountBean, UserBean userBean,String token) {
		this.accountBean = accountBean;
		this.userBean = userBean;
		this.token = token;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("account", accountBean);
		outState.putParcelable("user", userBean);
		outState.putString("token", token);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		switch (getCurrentState(savedInstanceState)) {
			case FIRST_TIME_START://加载DB中缓存数据
				
				break;
			case SCREEN_RETATE:
				
				break;
			case DESTROY_AND_CREATE:
				
				break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//下拉自动刷新数据
		//refresh();
		buildActionBar();
	}
	
	public void buildActionBar() {
		getActivity().getActionBar().setTitle(getString(R.string.t_home));
		getActivity().getActionBar().setIcon(R.drawable.ic_menu_home);
	}
}
