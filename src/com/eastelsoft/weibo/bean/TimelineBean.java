package com.eastelsoft.weibo.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;

public class TimelineBean {

	 private String created_at;

	    private long id;

	    private String idstr;

	    private String text;

	    private String source;

	    private boolean favorited;

	    private String truncated;

	    private String in_reply_to_status_id;

	    private String in_reply_to_user_id;

	    private String in_reply_to_screen_name;

	    private String mid;

	    private int reposts_count = 0;

	    private int comments_count = 0;
	    //    private Object annotations;

	    private String thumbnail_pic;

	    private String bmiddle_pic;

	    private String original_pic;

	    private String sourceString;

	    private long mills;

	    private TimelineBean retweeted_status;

	    private UserBean user;

	    private GeoBean geo;

	    private ArrayList<PicUrls> pic_urls = new ArrayList<PicUrls>();

	    private ArrayList<String> pic_ids = new ArrayList<String>();


	    private transient SpannableString listViewSpannableString;

	    public boolean isPicMuti() {
	    	return pic_urls.size() > 0;
	    }
	    
	    public static class PicUrls implements Parcelable {

	        public String thumbnail_pic;

	        @Override
	        public int describeContents() {
	            return 0;
	        }

	        @Override
	        public void writeToParcel(Parcel dest, int flags) {
	            dest.writeString(thumbnail_pic);
	        }

	        public static final Parcelable.Creator<PicUrls> CREATOR =
	                new Parcelable.Creator<PicUrls>() {
	                    public PicUrls createFromParcel(Parcel in) {
	                        PicUrls picUrls = new PicUrls();
	                        picUrls.thumbnail_pic = in.readString();
	                        return picUrls;
	                    }

	                    public PicUrls[] newArray(int size) {
	                        return new PicUrls[size];
	                    }
	                };

	    }


	    public String getCreated_at() {

	        return created_at;
	    }

	    public String getTimeInFormat() {
	        if (!TextUtils.isEmpty(created_at)) {
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	            return format.format(new Date(created_at));

	        }
	        return "";
	    }

	    public void setCreated_at(String created_at) {
	        this.created_at = created_at;
	    }

	    public String getId() {
	        return idstr;
	    }

	    public void setId(String id) {
	        this.idstr = id;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    public String getSource() {
	        return source;
	    }

	    public void setSource(String source) {
	        this.source = source;
	    }

	    public boolean isFavorited() {
	        return favorited;
	    }

	    public void setFavorited(boolean favorited) {
	        this.favorited = favorited;
	    }

	    public String getTruncated() {
	        return truncated;
	    }

	    public void setTruncated(String truncated) {
	        this.truncated = truncated;
	    }

	    public String getIn_reply_to_status_id() {
	        return in_reply_to_status_id;
	    }

	    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
	        this.in_reply_to_status_id = in_reply_to_status_id;
	    }

	    public String getIn_reply_to_user_id() {
	        return in_reply_to_user_id;
	    }

	    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
	        this.in_reply_to_user_id = in_reply_to_user_id;
	    }

	    public String getIn_reply_to_screen_name() {
	        return in_reply_to_screen_name;
	    }

	    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
	        this.in_reply_to_screen_name = in_reply_to_screen_name;
	    }

	    public GeoBean getGeo() {
	        return geo;
	    }

	    public void setGeo(GeoBean geo) {
	        this.geo = geo;
	    }

	    public String getMid() {
	        return mid;
	    }

	    public void setMid(String mid) {
	        this.mid = mid;
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


	    public UserBean getUser() {
	        return user;
	    }

	    public void setUser(UserBean user) {
	        this.user = user;
	    }

	    public TimelineBean getRetweeted_status() {
	        return retweeted_status;
	    }

	    public void setRetweeted_status(TimelineBean retweeted_status) {
	        this.retweeted_status = retweeted_status;
	    }


	    public long getIdLong() {
	        return this.id;
	    }



	    public void setListViewSpannableString(SpannableString listViewSpannableString) {
	        this.listViewSpannableString = listViewSpannableString;
	    }

	    public String getSourceString() {
	        if (!TextUtils.isEmpty(sourceString)) {
	            return sourceString;
	        } else {
	            if (!TextUtils.isEmpty(source)) {
	                sourceString = Html.fromHtml(this.source).toString();
	            }
	            return sourceString;
	        }
	    }

	    public void setSourceString(String sourceString) {
	        this.sourceString = sourceString;
	    }


	    public long getMills() {
	        if (mills == 0L) {
	        }
	        return mills;
	    }

	    public void setMills(long mills) {
	        this.mills = mills;
	    }

	    public String getThumbnail_pic() {
	        return thumbnail_pic;
	    }

	    public void setThumbnail_pic(String thumbnail_pic) {
	        this.thumbnail_pic = thumbnail_pic;
	    }

	    public String getBmiddle_pic() {
	        return bmiddle_pic;
	    }

	    public void setBmiddle_pic(String bmiddle_pic) {
	        this.bmiddle_pic = bmiddle_pic;
	    }

	    public String getOriginal_pic() {
	        return original_pic;
	    }

	    public void setOriginal_pic(String original_pic) {
	        this.original_pic = original_pic;
	    }


	    private ArrayList<String> thumbnaiUrls = new ArrayList<String>();

	    private ArrayList<String> middleUrls = new ArrayList<String>();

	    private ArrayList<String> highUrls = new ArrayList<String>();


	    public ArrayList<String> getThumbnailPicUrls() {
	        if (thumbnaiUrls.size() > 0) {
	            return thumbnaiUrls;
	        }
	        ArrayList<String> value = new ArrayList<String>();
	        for (PicUrls url : pic_urls) {
	            value.add(url.thumbnail_pic);
	        }

	        if (value.size() == 0) {
	            String prefStr = "http://ww4.sinaimg.cn/thumbnail/";
	            for (String url : pic_ids) {
	                value.add(prefStr + url + ".jpg");
	            }
	        }
	        this.thumbnaiUrls = value;
	        return value;
	    }

	    public ArrayList<String> getMiddlePicUrls() {
	        if (middleUrls.size() > 0) {
	            return middleUrls;
	        }
	        ArrayList<String> value = new ArrayList<String>();
	        for (PicUrls url : pic_urls) {
	            value.add(url.thumbnail_pic.replace("thumbnail", "bmiddle"));
	        }

	        if (value.size() == 0) {
	            String prefStr = "http://ww4.sinaimg.cn/bmiddle/";
	            for (String url : pic_ids) {
	                value.add(prefStr + url + ".jpg");
	            }
	        }

	        this.middleUrls = value;
	        return value;
	    }


	    public ArrayList<String> getHighPicUrls() {
	        if (highUrls.size() > 0) {
	            return highUrls;
	        }

	        ArrayList<String> value = new ArrayList<String>();

	        for (PicUrls url : pic_urls) {
	            value.add(url.thumbnail_pic.replace("thumbnail", "large"));
	        }

	        if (value.size() == 0) {
	            String prefStr = "http://ww4.sinaimg.cn/large/";
	            for (String url : pic_ids) {
	                value.add(prefStr + url + ".jpg");
	            }
	        }

	        this.highUrls = value;

	        return value;
	    }

	    public boolean isMultiPics() {
	        return pic_urls.size() > 1 || pic_ids.size() > 1;
	    }

	    public int getPicCount() {
	        return pic_urls.size() > 1 ? pic_urls.size() : pic_ids.size();
	    }

	    @Override
	    public int hashCode() {
	        return getId().hashCode();
	    }

    
}
