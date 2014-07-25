package com.eastelsoft.weibo.ui.login;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;

public class WebLoginActivity extends AbstractAppActivity {
	
	private WebView webView;
	
	private MenuItem refreshItem; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webloginactivity_layout);
		webView = (WebView)this.findViewById(R.id.webView);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.app_login);
		webView.setWebViewClient(new WeiboWebClient());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_menu_weblogin, menu);
		refreshItem = menu.findItem(R.id.menu_refresh);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	private class WeiboWebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}
	
}
