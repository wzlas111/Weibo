package com.eastelsoft.weibo;

import java.util.List;

import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.GroupListBean;
import com.eastelsoft.weibo.db.AccountDBTask;
import com.eastelsoft.weibo.utils.SettingHelper;

import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class GlobalContext extends Application {

	private static GlobalContext globalContext = null;
	
	private AccountBean accountBean;
	
	private GroupListBean groupListBean;
	
	//bitmap image cache
	private LruCache<String, Bitmap> appBitmapCache = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		globalContext = this;
		buildCache();
	}
	
	public static GlobalContext getInstance() {
		return globalContext;
	}
	
	public String getNickname() {
		return accountBean.getUsernick();
	}
	
	public String getToken() {
		return getAccountBean().getAccess_token();
	}
	
	public String getCurrentAccountId() {
		return getAccountBean().getUid();
	}
	
	public GroupListBean getGroup() {
		if (groupListBean == null) {//read from db
			
		}
		return groupListBean;
	}
	
	public AccountBean getAccountBean() {
		if (accountBean == null) {
			String id = SettingHelper.getDefaultAccountId();
			if (!TextUtils.isEmpty(id)) {
				accountBean = AccountDBTask.getAccount(id);
			} else {
				List<AccountBean> accountList = AccountDBTask.getAccountList();
				if (accountList != null && accountList.size() > 0) {
					accountBean = accountList.get(0);
				}
			}
		}
		return accountBean;
	}
	
	public void setAccountBean(AccountBean bean) {
		this.accountBean = bean;
	}
	
	public void setGroup(GroupListBean group) {
		this.groupListBean = group;
	}
	
	public synchronized LruCache<String, Bitmap> getBitmapCache() {
		if (appBitmapCache == null) {
			buildCache();
		}
		return appBitmapCache;
	}
	
	public void buildCache() {
		int memoryClass = ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = Math.max(1024 * 1024 * 8, 1024 * 1024 * memoryClass / 5);
		appBitmapCache = new LruCache<String, Bitmap>(cacheSize){
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}
	
}
