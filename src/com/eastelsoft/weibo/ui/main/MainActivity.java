package com.eastelsoft.weibo.ui.main;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.utils.BundleExtraConstants;
import com.eastelsoft.weibo.utils.SettingHelper;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

public class MainActivity extends SlidingFragmentActivity {
	
	private AccountBean accountBean;
	
	private FragmentManager fragmentManager;
	
	private RelativeLayout alipayLayout;
	private RelativeLayout serviceLayout;
	
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		if (savedInstanceState == null) {
			initFragments();
		}
		configSlidingMenu();
	}
	
	private void initFragments() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.commit();
		getSupportFragmentManager().executePendingTransactions();
	}
	
	private void configSlidingMenu() {
		
	}
	
	private void initView() {
	}

}
