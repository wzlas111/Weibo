package com.eastelsoft.weibo;

import java.util.List;

import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.db.AccountDBTask;
import com.eastelsoft.weibo.utils.SettingHelper;

import android.app.Application;
import android.text.TextUtils;

public class GlobalContext extends Application {

	private static GlobalContext globalContext = null;
	
	private AccountBean accountBean;
	
	@Override
	public void onCreate() {
		super.onCreate();
		globalContext = this;
	}
	
	public static GlobalContext getInstance() {
		return globalContext;
	}
	
	public String getNickname() {
		return accountBean.getUsernick();
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
}
