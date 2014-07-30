package com.eastelsoft.weibo.db;

import java.util.ArrayList;
import java.util.List;

import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.db.table.AccountTable;
import com.google.gson.Gson;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDBTask {

	private AccountDBTask() {}
	
	public static SQLiteDatabase getWsd() {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		return helper.getWritableDatabase();
	}
	
	public static SQLiteDatabase getRsd() {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		return helper.getReadableDatabase();
	}
	
	public static DBResult addOrUpdateAccount(AccountBean accountBean) {
		ContentValues cv = new ContentValues();
		cv.put(AccountTable.UID, accountBean.getUid());
		cv.put(AccountTable.OAUTH_TOKEN, accountBean.getAccess_token());
		cv.put(AccountTable.OAUTH_TOKEN_EXPIRES_TIME, String.valueOf(accountBean.getExpires_time()));
		cv.put(AccountTable.BLACK_MAGIC, false);
		
		String json = new Gson().toJson(accountBean.getInfo());
		cv.put(AccountTable.INFOJSON, json);
		
		Cursor c = getWsd().rawQuery("select * from "+AccountTable.TABLE_NAME+" where uid=?", 
				new String[]{accountBean.getUid()});
		if (c != null && c.getCount() > 0) { //update
			getWsd().update(AccountTable.TABLE_NAME, cv, AccountTable.UID+"=?", new String[]{accountBean.getUid()});
			return DBResult.update_successfully;
		} else { //add
			getWsd().insert(AccountTable.TABLE_NAME, AccountTable.UID, cv);
			return DBResult.add_successfully;
		}
	}
	
	public static List<AccountBean> getAccountList() {
		List<AccountBean> accountList = new ArrayList<AccountBean>();
		String sql = "select * from	"+AccountTable.TABLE_NAME;
		Cursor c = getWsd().rawQuery(sql, null);
		while(c.moveToNext()) {
			AccountBean bean = new AccountBean();
			int colid = c.getColumnIndex(AccountTable.OAUTH_TOKEN);
			bean.setAccess_token(c.getString(colid));
			
			colid = c.getColumnIndex(AccountTable.OAUTH_TOKEN_EXPIRES_TIME);
			bean.setExpires_time(Long.valueOf(c.getString(colid)));
			
			colid = c.getColumnIndex(AccountTable.BLACK_MAGIC);
			bean.setBlack_magic(c.getInt(colid) == 1);
			
			colid = c.getColumnIndex(AccountTable.NAVIGATION_POSITION);
			bean.setNavigationPosition(c.getInt(colid));
			
			Gson gson = new Gson();
			String infoJson = c.getString(c.getColumnIndex(AccountTable.INFOJSON));
			try {
				UserBean userBean = gson.fromJson(infoJson, UserBean.class);
				bean.setInfo(userBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			accountList.add(bean);
		}
		return accountList;
	}
	
	public static List<AccountBean> removeAccount(String uid) {
		String sql = "delete from " + AccountTable.TABLE_NAME + " where " + AccountTable.UID + " = ?";
		getWsd().execSQL(sql, new String[]{uid});
		
		return getAccountList();
	}
}
