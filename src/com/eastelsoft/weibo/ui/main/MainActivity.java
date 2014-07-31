package com.eastelsoft.weibo.ui.main;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.ui.fragment.AlipayFragment;
import com.eastelsoft.weibo.ui.fragment.ServiceFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private AlipayFragment alipayFragment;
	private ServiceFragment serviceFragment;
	private FragmentManager fragmentManager;
	
	private RelativeLayout alipayLayout;
	private RelativeLayout serviceLayout;
	
	public static Intent newInstance() {
		return new Intent(GlobalContext.getInstance(), MainActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);
		
		initView();
		fragmentManager = getSupportFragmentManager();
		getSupportFragmentManager().executePendingTransactions();
		setSection(0);
	}
	
	private void setSection(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (index) {
			case 0:
				if (alipayFragment == null) {
					alipayFragment = new AlipayFragment();
					transaction.add(R.id.main_content, alipayFragment);
				} else {
					transaction.show(alipayFragment);
				}
				break;
			case 1:
				if (serviceFragment == null) {
					serviceFragment = new ServiceFragment();
					transaction.add(R.id.main_content, serviceFragment);
				} else {
					transaction.show(serviceFragment);
				}
				break;
		}
		transaction.commit();
	}
	
	private void hideFragment(FragmentTransaction transaction) {
		if (alipayFragment != null) {
			transaction.hide(alipayFragment);
		}
		if (serviceFragment != null) {
			transaction.hide(serviceFragment);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.alipayLayout:
				setSection(0);
				break;
			case R.id.serviceLayout:
				setSection(1);
				break;
		}
		
	}
	
	private void initView() {
		alipayLayout = (RelativeLayout) this.findViewById(R.id.alipayLayout);
		serviceLayout = (RelativeLayout) this.findViewById(R.id.serviceLayout);
		alipayLayout.setOnClickListener(this);
		serviceLayout.setOnClickListener(this);
	}

}
