package com.eastelsoft.weibo.utils;

import com.eastelsoft.weibo.GlobalContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingHelper {
	
	private static SharedPreferences.Editor editor = null;
	private static SharedPreferences sharedPreferences = null;
	
	private static SharedPreferences.Editor getEditor(Context paramContext) {
		if (editor == null) {
			editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
		}
		return editor;
	}
	
	private static SharedPreferences getPreferences(Context paramContext) {
		if (sharedPreferences == null) {
			sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
		}
		return sharedPreferences;
	}
	
	private static void setEditor(Context paramContext,String key,String value) {
		getEditor(paramContext).putString(key, value).commit();
	}

	private static String getSharedPreferences(Context paramContext,String key,String defaultVal) {
		return getPreferences(paramContext).getString(key, defaultVal);
	}
	
	private static Context getContext() {
		return GlobalContext.getInstance();
	}
	
	public static void setDefaultAccountId(String uid) {
		setEditor(getContext(), "id", uid);
	}
	
	public static String getDefaultAccountId() {
		return getSharedPreferences(getContext(), "id", "");
	}
	
}
