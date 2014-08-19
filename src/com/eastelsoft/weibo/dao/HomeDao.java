package com.eastelsoft.weibo.dao;

import java.util.HashMap;
import java.util.Map;

import com.eastelsoft.weibo.bean.MessageListBean;
import com.eastelsoft.weibo.http.HttpMethod;
import com.eastelsoft.weibo.http.HttpUtility;
import com.eastelsoft.weibo.utils.URLHelper;
import com.google.gson.Gson;

public class HomeDao {

	private String access_token;
	private String since_id = "0";
	private String max_id = "0";
	private String count = "1";
	private String page = "1";
	private String base_app = "0";
	private String feature = "0";
	private String trim_user = "0";
	
	public HomeDao(String token) {
		this.access_token = token;
	}
	
	public String getJSON() throws Exception{
		String urlString = URLHelper.FRIENDS_TIMELINE;
		
		System.out.println("url dao maxId : "+max_id);
		
		Map<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("since_id", since_id);
		params.put("max_id", max_id);
		params.put("count", count);
		params.put("page", page);
		params.put("base_app", base_app);
		params.put("feature", feature);
		params.put("trim_user", trim_user);
		
		String result = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, urlString, params);
		
		return result;
	}
	
	public MessageListBean getBean() throws Exception{
		String jsonStr = getJSON();
		Gson gson = new Gson();
		
		MessageListBean bean = null;
		try {
			bean = gson.fromJson(jsonStr, MessageListBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getSince_id() {
		return since_id;
	}

	public void setSince_id(String since_id) {
		this.since_id = since_id;
	}

	public String getMax_id() {
		return max_id;
	}

	public void setMax_id(String max_id) {
		this.max_id = max_id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getBase_app() {
		return base_app;
	}

	public void setBase_app(String base_app) {
		this.base_app = base_app;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTrim_user() {
		return trim_user;
	}

	public void setTrim_user(String trim_user) {
		this.trim_user = trim_user;
	}
	
}
