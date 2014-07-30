package com.eastelsoft.weibo.ui.login;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.dao.AccountDao;
import com.eastelsoft.weibo.db.AccountDBTask;
import com.eastelsoft.weibo.db.DBResult;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;
import com.eastelsoft.weibo.utils.URLHelper;
import com.eastelsoft.weibo.utils.Utility;

public class WebLoginActivity extends AbstractAppActivity {
	
	private WebView webView;
	
	private MenuItem refreshItem; 
	
	ProgressFragment progress;

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
        System.out.println("url secret : "+URLHelper.APP_SECRET);
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
			if (url.startsWith(URLHelper.DIRECT_URL)) {
				handleUrl(url);
				view.stopLoading();
				return;
			}
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
	
	private void handleUrl(String url) {
		try {
			URL u = new URL(url);
//			System.out.println("url == > query : "+u.getQuery()+", ref : "+u.getRef());
			Bundle bundle = Utility.decodeUrl(u.getRef());
			String error = bundle.getString("error");
			String error_code = bundle.getString("error_code");
			if (error == null && error_code == null) {
				String access_token = bundle.getString("access_token");
				String expires_in = bundle.getString("expires_in");
				Intent i = new Intent();
				i.putExtras(bundle);
				setResult(RESULT_OK, i);
				new OAuthTask().execute(access_token,expires_in);
			}else{
				finish();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取用户详细信息
	 * @author wangzl
	 *
	 */
	private class OAuthTask extends AsyncTask<String, Integer, DBResult> {

		@Override
		protected void onPreExecute() {
			progress = new ProgressFragment();
			progress.show(getSupportFragmentManager(), "");
		}
		
		@Override
		protected DBResult doInBackground(String... params) {
			String access_token = params[0];
			long expires_in = Long.valueOf(params[1]);
			try {
				//http get userinfo
				UserBean userBean = new AccountDao(access_token).getShow();
				
				AccountBean accountBean = new AccountBean();
				accountBean.setAccess_token(access_token);
				accountBean.setExpires_time(System.currentTimeMillis()+expires_in*1000);
				accountBean.setInfo(userBean);
				System.out.println("userinfo ,expires : "+expires_in);
				System.out.println("userinfo ,info : "+userBean.getScreen_name());
				return AccountDBTask.addOrUpdateAccount(accountBean);
			} catch (Exception e) {
				e.printStackTrace();
				onCancelled(null);
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(DBResult result) {
			if (progress.isVisible()) {
				progress.dismissAllowingStateLoss();
			}
			switch (result) {
				case add_successfully:
					Toast.makeText(WebLoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
					break;
				case update_successfully:
					Toast.makeText(WebLoginActivity.this, getString(R.string.update_account_success), Toast.LENGTH_SHORT).show();
					break;
			}
			WebLoginActivity.this.finish();
		}
		
		@Override
		protected void onCancelled(DBResult result) {
			super.onCancelled(result);
			if (progress != null) {
				progress.dismissAllowingStateLoss();
			}
			Toast.makeText(WebLoginActivity.this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public static class ProgressFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage(getString(R.string.authenticationing));
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
	}
	
}
