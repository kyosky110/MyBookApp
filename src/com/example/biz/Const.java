package com.example.biz;

import android.os.Environment;

public class Const {
	/**用户登录url*/
	public static final String LOGIN_URL = "http://wenxinshuwu.duapp.com/mlogin";
	/**注册url*/
	public static final String REGISTER_URL = "http://wenxinshuwu.duapp.com/register";
	/**添加图书*/
	public static final String ADDBOOK_URL = "http://wenxinshuwu.duapp.com/addbook";
	/**查询图书*/
	public static final String QUERYBOOK_URL = "http://wenxinshuwu.duapp.com/querybook";
	/**搜索查询**/
	public static final String QUERYBOOKBYLIKE = "http://wenxinshuwu.duapp.com/queryByLike";
	/**个人书的信息查询**/
	public static final String QUERYBOOKBYUSERNAME = "http://wenxinshuwu.duapp.com/queryBookByUsername";
	
	public static final String CHANGISLEND = "http://wenxinshuwu.duapp.com/updateBook";
	
	public static final String CHANGISNOTLEND = "http://wenxinshuwu.duapp.com/delBook";
	
	public static final  String SFNAME = "config";
	
	public static final String PASSWORD = "password";
	
	public static final String USERNAME = "username";
	
	public static final String SD_IMAGEDIR = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Android/data/com.sky.mybook/downloadImage/";
	
	public static final String IMAGEPATH_URL = "http://bcs.duapp.com/bookbucket/bimage/";
	
	public static final String SERVERVERSIONURL = "http://bcs.duapp.com/bookbucket/upload/serverversion.xml";
	
	public static final String DOWNAPK = "http://bcs.duapp.com/bookbucket/upload/MyBookApp.apk";
	
	public static final String APKNAME = "MyBookApp.apk";
}
