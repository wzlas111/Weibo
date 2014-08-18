package com.eastelsoft.weibo.bean;

import java.util.ArrayList;
import java.util.List;

public class GroupListBean {

	private List<GroupBean> lists = new ArrayList<GroupBean>();
	private String total_number;
	
	public List<GroupBean> getLists() {
		return lists;
	}
	public void setLists(List<GroupBean> lists) {
		this.lists = lists;
	}
	public String getTotal_number() {
		return total_number;
	}
	public void setTotal_number(String total_number) {
		this.total_number = total_number;
	}
	
}
