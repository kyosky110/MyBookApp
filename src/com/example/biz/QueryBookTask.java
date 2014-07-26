package com.example.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;

import com.example.bean.Book;
import com.example.util.XmlParseUtil;

public class QueryBookTask extends AsyncTask<Map<String,String>, Integer, List<Book>> {

	private HttpTaskListener listener;
	
	public QueryBookTask(HttpTaskListener listener) {
		super();
		this.listener = listener;
	}

	@Override
	protected List<Book> doInBackground(Map<String, String>... params) {
		String url = params[0].get("url");
		List<Book> list = new ArrayList<Book>();
		InternetHelper helper = new InternetHelper();
		list = XmlParseUtil.getBooks(helper.httpSends(url, params[1]));
		return list;
	}

	@Override
	protected void onPostExecute(List<Book> result) {
		if(result.size()!=0){
			listener.onSuccess(result);
		}else{
			listener.onException();
		}
		
	}
}
