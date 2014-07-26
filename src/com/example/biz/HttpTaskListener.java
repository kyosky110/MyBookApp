package com.example.biz;

import java.util.List;

import com.example.bean.Book;

public interface HttpTaskListener {
	
	/**数据返回成功*/
	public void onSuccess(List<Book> list);
	
	/**数据返回失败*/
	public void onException();
}
