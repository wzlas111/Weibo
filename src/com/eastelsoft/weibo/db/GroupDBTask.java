package com.eastelsoft.weibo.db;

import com.eastelsoft.weibo.bean.GroupListBean;
import com.eastelsoft.weibo.db.table.GroupTable;
import com.google.gson.Gson;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class GroupDBTask {

	private GroupDBTask() {}
	
	public static SQLiteDatabase getWsd() {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		return helper.getWritableDatabase();
	}
	
	public static SQLiteDatabase getRsd() {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		return helper.getReadableDatabase();
	}
	
	public static DBResult addOrUpdate(GroupListBean bean,String accountId) {
		ContentValues values = new ContentValues();
		values.put(GroupTable.ACCOUNT_ID, accountId);
		values.put(GroupTable.JSON, new Gson().toJson(bean));
		
		System.out.println("group json add: "+values.getAsString(GroupTable.JSON));
		String sql = "select * from "+GroupTable.TABLE_NAME+" where "+GroupTable.ACCOUNT_ID+"=?";
		Cursor c = getWsd().rawQuery(sql, new String[]{accountId});
		if (c != null && c.getCount() > 0) { //update
			getWsd().update(GroupTable.TABLE_NAME, values, GroupTable.ACCOUNT_ID+"=?", new String[]{accountId});
			return DBResult.update_successfully;
		} else { //add
			getWsd().insert(GroupTable.TABLE_NAME, GroupTable.ACCOUNT_ID, values);
			return DBResult.add_successfully;
		}
	}
	
	public static GroupListBean query(String accountId) {
		String sql = "select * from "+GroupTable.TABLE_NAME+" where "+GroupTable.ACCOUNT_ID+"=?";
		Cursor c = getWsd().rawQuery(sql, new String[]{accountId});
		while (c.moveToNext()) {
			String json = c.getString(c.getColumnIndex(GroupTable.JSON));
			System.out.println("group json read: "+json);
			if (!TextUtils.isEmpty(json)) {
				GroupListBean bean = new Gson().fromJson(json, GroupListBean.class);
				if (bean != null) {
					return bean;
				}
			}
		}
		return null;
	}
}
