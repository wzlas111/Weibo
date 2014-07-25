package com.eastelsoft.weibo.ui.login;

import android.app.ActionBar;
import android.os.Bundle;
import android.webkit.WebView;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;

public class WebLoginActivity extends AbstractAppActivity {
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webloginactivity_layout);
		webView = (WebView)this.findViewById(R.id.webView);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.app_login);
	}
}
