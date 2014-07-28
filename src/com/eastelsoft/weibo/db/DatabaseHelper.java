package com.eastelsoft.weibo.db;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.db.table.AccountTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper singleton = null;
	
	private static String DB_NAME = "weibo.db";
	private static int DB_VERSION = 1;
	
	static final String CREATE_ACCOUNT_TABLE_SQL = "create table " + AccountTable.TABLE_NAME
            + "("
            + AccountTable.UID + " integer primary key autoincrement,"
            + AccountTable.OAUTH_TOKEN + " text,"
            + AccountTable.OAUTH_TOKEN_EXPIRES_TIME + " text,"
            + AccountTable.OAUTH_TOKEN_SECRET + " text,"
            + AccountTable.BLACK_MAGIC + " boolean,"
            + AccountTable.NAVIGATION_POSITION + " integer,"
            + AccountTable.INFOJSON + " text"
            + ");";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_ACCOUNT_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public static synchronized DatabaseHelper getInstance() {
		if (singleton == null) {
			singleton = new DatabaseHelper(GlobalContext.getInstance());
		}
		return singleton;
	}

}
