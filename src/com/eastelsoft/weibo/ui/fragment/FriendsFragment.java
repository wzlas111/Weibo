package com.eastelsoft.weibo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.ui.adapter.FriendsBarAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FriendsFragment extends BaseFragment {
	
	private FriendsBarAdapter barAdapter;
	
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
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.friends_fragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.friendsbar_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.write_weibo:
				
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * custom actionbar
	 */
	public void buildActionBar() {
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(R.drawable.ic_menu_home);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		barAdapter = new FriendsBarAdapter(getActivity(), buildGroupData());
		actionBar.setListNavigationCallbacks(barAdapter, new OnNavigationListener(){
			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
				Toast.makeText(getActivity(), "selected : "+buildGroupData()[itemPosition], Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
	}
	
	@Override
	public void buildAdapter() {
		
	}
	
	private String[] buildGroupData() {
		List<String> list = new ArrayList<String>();
		
		list.add(getString(R.string.g_all));
		list.add(getString(R.string.g_each));
		list.add(getString(R.string.g_special));
		list.add(getString(R.string.g_star));
		list.add(getString(R.string.g_it));
		
		return list.toArray(new String[0]);
	}
}
