package com.eastelsoft.weibo.utils;

public class URLHelper {

	//base url
    private static final String URL_SINA_WEIBO = "https://api.weibo.com/2/";

    //login
    public static final String URL_OAUTH2_ACCESS_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";
    public static final String APP_KEY = "1065511513";
    public static final String APP_SECRET = "df428e88aae8bd31f20481d149c856ed";
    public static final String DIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    
    //user info
    public static final String GET_UID = URL_SINA_WEIBO + "account/get_uid.json";
    public static final String GET_SHOW = URL_SINA_WEIBO + "users/show.json";
}
