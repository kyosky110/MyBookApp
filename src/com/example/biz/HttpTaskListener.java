package com.example.biz;

import java.util.List;

import com.example.bean.Book;

public interface HttpTaskListener {
	
	/**���ݷ��سɹ�*/
	public void onSuccess(List<Book> list);
	
	/**���ݷ���ʧ��*/
	public void onException();
}
