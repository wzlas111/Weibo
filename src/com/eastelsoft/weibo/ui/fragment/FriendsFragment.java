package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.friends_fragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		switch (getCurrentState(savedInstanceState)) {
			case FIRST_TIME_START://����DB�л�������
				
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
		//�����Զ�ˢ������
		//refresh();
	}
	
	public void buildActionBar() {
		getActivity().getActionBar().setTitle(getString(R.string.t_home));
		getActivity().getActionBar().setIcon(R.drawable.ic_menu_home);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		System.out.println("FriendsFragment =====> buildActionBar");
	}
}
