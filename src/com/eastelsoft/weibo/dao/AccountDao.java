package com.eastelsoft.weibo.dao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.http.HttpMethod;
import com.eastelsoft.weibo.http.HttpUtility;
import com.eastelsoft.weibo.utils.URLHelper;
import com.google.gson.Gson;

public class AccountDao {

	private String access_token;
	
	public AccountDao(String access_token) {
		this.access_token = access_token;
	}
	
	public UserBean getShow() throws Exception{
		String uidjson = getUid();
		String uid = "";
		
		try {
			JSONObject obj = new JSONObject(uidjson);
			uid = obj.getString("uid");
		} catch (Exception e) {
		}
		
		String url = URLHelper.GET_SHOW;
		Map<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("uid", uid);
		String result = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, params);
		
		Gson gson = new Gson();
		UserBean userBean = new UserBean();
		try {
			userBean = gson.fromJson(result, UserBean.class);
		} catch (Exception e) {
		}
		
		return userBean;
	} 
	
	public String getUid() throws Exception {
		String url = URLHelper.GET_UID;
		Map<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		
		return HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, params);
	}
}
