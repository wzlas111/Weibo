package com.eastelsoft.weibo.ui.login;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;
import com.eastelsoft.weibo.utils.URLHelper;
import com.eastelsoft.weibo.utils.Utility;

public class WebLoginActivity extends AbstractAppActivity {
	
	private WebView webView;
	
	private MenuItem refreshItem; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webloginactivity_layout);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.app_login);
		webView = (WebView) this.findViewById(R.id.webView);
		webView.setWebViewClient(new WeiboWebClient());
		
		WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		webView.clearCache(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_menu_weblogin, menu);
		refreshItem = menu.findItem(R.id.menu_refresh);
		refresh();
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refresh();
		}
		return true;
	}
	
	public void refresh() {
		webView.clearView();
		webView.loadUrl("about:blank");
		LayoutInflater viewInflater = getLayoutInflater();
		ImageView iv = (ImageView) viewInflater.inflate(R.layout.refresh_view, null);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.refresh);
		iv.setAnimation(animation);
		
		refreshItem.setActionView(iv);
		webView.loadUrl(getAuthUrl());
	}
	
	public void completeRefresh() {
		if (refreshItem.getActionView() != null) {
			refreshItem.getActionView().clearAnimation();
			refreshItem.setActionView(null);
		}
	}
	
	private String getAuthUrl() {
		Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", URLHelper.APP_KEY);
        parameters.put("response_type", "token");
        parameters.put("redirect_uri", URLHelper.DIRECT_URL);
        parameters.put("display", "mobile");
        
        return URLHelper.URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(parameters)
                + "&scope=friendships_groups_read,friendships_groups_write";
	}
	
	private class WeiboWebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			System.out.println("start url : " + url);
			super.onPageStarted(view, url, favicon);
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			System.out.println("end url : " + url);
			super.onPageFinished(view, url);
			if (!url.equals("about:blank")) {
                completeRefresh();
            }
		}
	}
	
}
