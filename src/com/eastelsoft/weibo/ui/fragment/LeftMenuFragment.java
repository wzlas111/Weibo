package com.eastelsoft.weibo.ui.fragment;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.R.layout;
import com.eastelsoft.weibo.ui.main.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment {
	
	private int currentIndex = -1;
	private static final int HOME_INDEX = 0;
	private static final int MENTIONS_INDEX = 1;
	private static final int COMMENTS_INDEX = 2;
	private static final int DM_INDEX = 3;
	private static final int FAV_INDEX = 4;
	private static final int SEARCH_INDEX = 5;
	private static final int PROFILE_INDEX = 6;
	private static final int LOGOUT_INDEX = 7;
	private static final int SETTINGS_INDEX = 8;
	
	private SparseArray<Fragment> rightFragment = new SparseArray<>();
	
	private Spinner sp_avatar;
	private TextView tv_nickname;
	private LinearLayout btn_home;
	private LinearLayout btn_mentions;
	private LinearLayout btn_comments;
	private LinearLayout btn_dm;
	private LinearLayout btn_fav;
	private LinearLayout btn_search;
	private LinearLayout btn_profile;

	public static LeftMenuFragment newInstance() {
		LeftMenuFragment fragment = new LeftMenuFragment();
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
		ScrollView view = (ScrollView)inflater.inflate(R.layout.leftmenu_layout, container, false);
		sp_avatar = (Spinner) view.findViewById(R.id.avatar);
		tv_nickname = (TextView)view.findViewById(R.id.nickname);
		btn_home = (LinearLayout)view.findViewById(R.id.btn_home);
		btn_mentions = (LinearLayout)view.findViewById(R.id.btn_mentions);
		btn_comments = (LinearLayout)view.findViewById(R.id.btn_comments);
		btn_dm = (LinearLayout)view.findViewById(R.id.btn_dm);
		btn_fav = (LinearLayout)view.findViewById(R.id.btn_fav);
		btn_search = (LinearLayout)view.findViewById(R.id.btn_search);
		btn_profile = (LinearLayout)view.findViewById(R.id.btn_profile);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		btn_home.setOnClickListener(onClickListener);
		btn_mentions.setOnClickListener(onClickListener);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentIndex", currentIndex);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			currentIndex = savedInstanceState.getInt("currentIndex");
		}
		
		rightFragment.append(HOME_INDEX, ((MainActivity)getActivity()).getFriendsFragment());
		rightFragment.append(MENTIONS_INDEX, ((MainActivity)getActivity()).getMentionsFragment());
		
		if (currentIndex == -1) {
			currentIndex = HOME_INDEX;
		}
		
		switchIndex(currentIndex);
		
		sp_avatar.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
		tv_nickname.setText(GlobalContext.getInstance().getNickname());
	}
	
	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_home:
					switchIndex(HOME_INDEX);
					break;
				case R.id.btn_mentions: 
					switchIndex(MENTIONS_INDEX);
					break;
			}
		}
	};
	
	private void switchIndex(int index) {
		switch (index) {
			case HOME_INDEX:
				showHomePage();
				break;
			case MENTIONS_INDEX:
				showMentionsPage();
				break;
		}
		drawSelectedItem(index);
	}
	
	private void drawSelectedItem(int index) {
		btn_home.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_mentions.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_comments.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_dm.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_fav.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_search.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		btn_profile.setBackground(getResources().getDrawable(R.drawable.leftmenu_item));
		switch (index) {
			case HOME_INDEX:
				btn_home.setBackground(getResources().getDrawable(R.color.ics_blue_semi));
				break;
			case MENTIONS_INDEX:
				btn_mentions.setBackground(getResources().getDrawable(R.color.ics_blue_semi));
				break;
		}
	}
	
	private void showHomePage() {
		currentIndex = HOME_INDEX;
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().showContent();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.hide(rightFragment.get(MENTIONS_INDEX));
		FriendsFragment fragment = (FriendsFragment)rightFragment.get(HOME_INDEX);
		transaction.show(fragment);
		transaction.commit();
		fragment.buildActionBar();
	}
	
	private void showMentionsPage() {
		currentIndex = MENTIONS_INDEX;
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().showContent();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.hide(rightFragment.get(HOME_INDEX));
		MentionsFragment fragment = (MentionsFragment)rightFragment.get(MENTIONS_INDEX);
		transaction.show(fragment);
		transaction.commit();
		fragment.buildActionBar();
	}
	
	private SlidingMenu getSlidingMenu() {
        return ((MainActivity) getActivity()).getSlidingMenu();
    }
}
