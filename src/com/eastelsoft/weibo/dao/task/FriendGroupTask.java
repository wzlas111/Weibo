package com.eastelsoft.weibo.dao.task;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.bean.GroupListBean;
import com.eastelsoft.weibo.dao.GroupDao;
import com.eastelsoft.weibo.db.GroupDBTask;

import android.os.AsyncTask;

public class FriendGroupTask extends AsyncTask<Void, Integer, GroupListBean> {
	
	private String token;
	private String accountId;
	
	public FriendGroupTask(String token, String accountId) {
		this.token = token;
		this.accountId = accountId;
	}

	@Override
	protected GroupListBean doInBackground(Void... params) {
		try {
			GroupListBean b = new GroupDao(token).getBean();
			System.out.println("group size : "+b.getLists().size());
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			cancel(true);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(GroupListBean result) {
		super.onPostExecute(result);
		if (result == null) {
			return;
		}
		//更新数据库
		GroupDBTask.addOrUpdate(result, accountId);
		//读入程序缓存application
		GlobalContext.getInstance().setGroup(result);
	}

}
