package com.eastelsoft.weibo.db;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.db.table.AccountTable;
import com.eastelsoft.weibo.db.table.GroupTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper singleton = null;
	
	private static String DB_NAME = "weibo.db";
	private static int DB_VERSION = 2;
	
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
	
	static final String CREATE_GROUP_TABLE_SQL = "create table " + GroupTable.TABLE_NAME
			+ "("
			+ GroupTable.ACCOUNT_ID + " text primary key,"
			+ GroupTable.JSON + " text"
			+ ");";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_ACCOUNT_TABLE_SQL);
		db.execSQL(CREATE_GROUP_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion <= 2) {
			db.execSQL(CREATE_GROUP_TABLE_SQL);
		}
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
