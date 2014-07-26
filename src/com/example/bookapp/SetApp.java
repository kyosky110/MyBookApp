package com.example.bookapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 

public class SetApp extends Activity {
	private ListView myset_list;
	private String title[] = { "账号管理", "密码修改", "清理缓存", "版本更新", "意见反馈", "常见问题",
			"联系我们", "退出当前帐号" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_list);
		myset_list = (ListView) findViewById(R.id.set_list);

		myset_list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, title));

	}

}
