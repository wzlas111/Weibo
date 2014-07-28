package com.eastelsoft.weibo.utils;

public class URLHelper {

	public static final String TEST_URL = "http://58.240.63.104";
	public static final String TEST_URL_2 = "http://www.baidu.com/";
	
	//base url
    private static final String URL_SINA_WEIBO = "https://api.weibo.com/2/";

    //login
    public static final String UID = URL_SINA_WEIBO + "account/get_uid.json";
    public static final String URL_OAUTH2_ACCESS_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";

    public static final String APP_KEY = Utility.rot47("`_edd``d`b");

    public static final String APP_SECRET = Utility.rot47("57cag6gg226g35b`7a_cg`5`ch4gde65");

    public static final String DIRECT_URL = Utility
            .rot47("9EEADi^^2A:]H6:3@]4@>^@2FE9a^5672F=E]9E>=");
}
