package com.eastelsoft.weibo.bean;

import java.util.ArrayList;
import java.util.List;

public class MessageListBean {

	private List<TimelineBean> statuses = new ArrayList<TimelineBean>();

    private List<AdBean> ad = new ArrayList<AdBean>();
    
    protected int total_number = 0;
    protected String previous_cursor = "0";
    protected String next_cursor = "0";

	public List<TimelineBean> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<TimelineBean> statuses) {
		this.statuses = statuses;
	}

	public List<AdBean> getAd() {
		return ad;
	}

	public void setAd(List<AdBean> ad) {
		this.ad = ad;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

	public String getPrevious_cursor() {
		return previous_cursor;
	}

	public void setPrevious_cursor(String previous_cursor) {
		this.previous_cursor = previous_cursor;
	}

	public String getNext_cursor() {
		return next_cursor;
	}

	public void setNext_cursor(String next_cursor) {
		this.next_cursor = next_cursor;
	}
	
}
