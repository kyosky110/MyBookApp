package com.example.bookapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 

public class SetApp extends Activity {
	private ListView myset_list;
	private String title[] = { "�˺Ź���", "�����޸�", "������", "�汾����", "�������", "��������",
			"��ϵ����", "�˳���ǰ�ʺ�" };

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
