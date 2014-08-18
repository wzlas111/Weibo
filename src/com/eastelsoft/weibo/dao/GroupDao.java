package com.eastelsoft.weibo.dao;

import java.util.HashMap;
import java.util.Map;

import com.eastelsoft.weibo.bean.GroupListBean;
import com.eastelsoft.weibo.http.HttpMethod;
import com.eastelsoft.weibo.http.HttpUtility;
import com.eastelsoft.weibo.utils.URLHelper;
import com.google.gson.Gson;

public class GroupDao {

	private String token;
	
	public GroupDao(String token) {
		this.token = token;
	}
	
	public GroupListBean getBean() throws Exception{
		GroupListBean bean = new GroupListBean();
		
		String url = URLHelper.FRIENDS_GROUP;
		
		Map<String, String> params = new HashMap<>();
		params.put("access_token", token);
		
		String jsonString = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, params);
		try {
			Gson gson = new Gson();
			bean = gson.fromJson(jsonString, GroupListBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
}
