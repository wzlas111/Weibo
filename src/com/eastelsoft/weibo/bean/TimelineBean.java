package com.eastelsoft.weibo.bean;

import java.util.ArrayList;
import java.util.List;

public class TimelineBean {

	private String created_at;
	private long id;
	private String idStr;
	private String mid;
	private String source;
	private String favorited;
	private String truncated;
	private int reposts_count;
	private int comments_count;
	private String attitudes_count;
	private List<String> pic_urls = new ArrayList<String>();
	
	private UserBean user;

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFavorited() {
		return favorited;
	}

	public void setFavorited(String favorited) {
		this.favorited = favorited;
	}

	public String getTruncated() {
		return truncated;
	}

	public void setTruncated(String truncated) {
		this.truncated = truncated;
	}

	public int getReposts_count() {
		return reposts_count;
	}

	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public String getAttitudes_count() {
		return attitudes_count;
	}

	public void setAttitudes_count(String attitudes_count) {
		this.attitudes_count = attitudes_count;
	}

	public List<String> getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(List<String> pic_urls) {
		this.pic_urls = pic_urls;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
}
