package com.example.bookapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MySeachView extends Activity {
	EditText usersearwords;
	Button search_btn;
	ListView user_search_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		usersearwords = (EditText) findViewById(R.id.seartext);
		search_btn = (Button) findViewById(R.id.click_search);
		user_search_list = (ListView) findViewById(R.id.user_search_list);
		search_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
            String text=usersearwords.getText().toString();
            Intent intent = new Intent(MySeachView.this,SeachBookListActivity.class);
            intent.putExtra("content", text);
            startActivity(intent);
			}
		});
	}
}
