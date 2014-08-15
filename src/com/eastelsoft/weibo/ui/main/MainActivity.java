package com.eastelsoft.weibo.ui.main;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.ui.fragment.FriendsFragment;
import com.eastelsoft.weibo.ui.fragment.LeftMenuFragment;
import com.eastelsoft.weibo.ui.fragment.MentionsFragment;
import com.eastelsoft.weibo.utils.BundleExtraConstants;
import com.eastelsoft.weibo.utils.SettingHelper;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends SlidingFragmentActivity {
	
	private AccountBean accountBean;
	
	public static Intent newInstance() {
		return new Intent(GlobalContext.getInstance(), MainActivity.class);
	}
	
	public static Intent newInstance(AccountBean accountBean) {
		Intent intent = newInstance();
		intent.putExtra(BundleExtraConstants.ACCOUNT_EXTRA, accountBean);
		return intent;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("account", accountBean);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			accountBean = savedInstanceState.getParcelable("account");
		} else { //第一次登陆
			Intent intent = getIntent();
			accountBean = intent.getParcelableExtra(BundleExtraConstants.ACCOUNT_EXTRA);
		}
		
		//当二次登录时，需取出默认账号
		if (accountBean == null) {
			accountBean = GlobalContext.getInstance().getAccountBean();
		}
		
		//设置默认账号
		GlobalContext.getInstance().setAccountBean(accountBean);
		SettingHelper.setDefaultAccountId(accountBean.getUid());
		
		buildUI(savedInstanceState);
	}
	
	
	private void buildUI(Bundle savedInstanceState) {
		setContentView(R.layout.content_frame);
		setBehindContentView(R.layout.menu_frame);
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		if (savedInstanceState == null) {
			initFragments();
			
			FragmentTransaction leftFt = getFragmentManager().beginTransaction();
			leftFt.replace(R.id.menu_frame, getLeftMenuFragment(), LeftMenuFragment.class.getName());
			getSlidingMenu().showContent();
			leftFt.commit();
		}
		configSlidingMenu();
	}
	
	private void initFragments() {
		Fragment friend = getFriendsFragment();
		Fragment mentions = getMentionsFragment();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if (!friend.isAdded()) {
			transaction.add(R.id.content_frame, friend, FriendsFragment.class.getName());
		}
		if (!mentions.isAdded()) {
			transaction.add(R.id.content_frame, mentions, MentionsFragment.class.getName());
		}
		
		if (!transaction.isEmpty()) {
			transaction.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
	}
	
	private void configSlidingMenu() {
		getSlidingMenu().setBehindOffset(300);
		getSlidingMenu().setFadeDegree(0.35f);
	}
	
	public LeftMenuFragment getLeftMenuFragment() {
		LeftMenuFragment fragment = (LeftMenuFragment)getFragmentManager().findFragmentByTag(LeftMenuFragment.class.getName());
		if (fragment == null) {
			fragment = LeftMenuFragment.newInstance();
		}
		return fragment;
	}
	
	public FriendsFragment getFriendsFragment() {
		FriendsFragment fragment = (FriendsFragment)getFragmentManager().findFragmentByTag(FriendsFragment.class.getName()); 
		if (fragment == null) {
			fragment = FriendsFragment.newInstance(getAccountBean(), getUserBean(), getToken());
		}
		return fragment;
	}
	
	public MentionsFragment getMentionsFragment() {
		MentionsFragment fragment = (MentionsFragment)getFragmentManager().findFragmentByTag(MentionsFragment.class.getName());
		if (fragment == null) {
			fragment = MentionsFragment.newInstance();
		}
		return fragment;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSlidingMenu().showMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	private AccountBean getAccountBean() {
		return accountBean;
	}
	
	private UserBean getUserBean() {
		return accountBean.getInfo();
	}
	
	private String getToken() {
		return accountBean.getAccess_token();
	}
}
