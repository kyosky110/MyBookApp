package com.example.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.biz.HttpTaskListener;
import com.example.bean.Book;
import com.example.util.XmlParseUtil;


public class AddBookTask extends AsyncTask<Map<String,String>, Integer, Book> {
	
	
	/** 请求返回处理监听器 */
	private HttpTaskListener listener;
	
	public AddBookTask(com.example.biz.HttpTaskListener listener) {
		super();
		this.listener = listener;
	}


	@Override
	protected Book doInBackground(Map<String, String>... params) {
		String url = params[0].get("url");
		Book book = null;
		InternetHelper helper = new InternetHelper();
		book = XmlParseUtil.getAddBook(helper.httpSends(url, params[1]));
		return book;
	}


	@Override
	protected void onPostExecute(Book result) {
		
		if(result==null){
			listener.onException();
		}else{
			List<Book> list = new ArrayList<Book>();
			list.add(result);
			listener.onSuccess(list);
		}
		listener = null;
	}
}
