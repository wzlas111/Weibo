package com.eastelsoft.weibo.db;

import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.db.table.AccountTable;
import com.eastelsoft.weibo.utils.DBResult;
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
}
