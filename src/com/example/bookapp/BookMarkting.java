package com.example.bookapp;

//import android.app.ActionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import listactivity.CollegeList;
import listactivity.EnglishList;
import listactivity.IntenetList;
import listactivity.MathList;
import listactivity.OtherList;
import listactivity.PolicyList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class BookMarkting extends Activity {
	// ActionBar actionBar;
	private GridView myGridView;
	ImageView imageView;
	int[] imageIDs = new int[] { R.drawable.kind_english, R.drawable.kind_math,
			R.drawable.kind_policy, R.drawable.kind_collegebook,
			R.drawable.kind_internet, R.drawable.kind_other };
	String tag = "BookMarkting";
	private String username;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandablelist);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		myGridView = (GridView) findViewById(R.id.gridView1);
		imageView = (ImageView) findViewById(R.id.imageView1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageIDs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageIDs[i]);
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.cell,
				new String[] { "image" }, new int[] { R.id.image1 });
		myGridView.setAdapter(adapter);

		myGridView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(tag, " «∑Ò÷¥––---------°∑°∑°∑°∑°∑");
				imageView.setImageResource(imageIDs[position]);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		myGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (imageIDs[position]) {
				case R.drawable.kind_collegebook:
					Intent intent1 = new Intent(BookMarkting.this,  BookList.class);
					intent1.putExtra("classify", 3+"");
					startActivity(intent1);
					break;
				case R.drawable.kind_english:
					Intent intent2 = new Intent(BookMarkting.this, BookList.class);
					intent2.putExtra("classify", 0+"");
					startActivity(intent2);
					break;
				case R.drawable.kind_internet:
					Intent intent3 = new Intent(BookMarkting.this, BookList.class);
					intent3.putExtra("classify", 4+"");
					startActivity(intent3);
					break;
				case R.drawable.kind_math:
					Intent intent4 = new Intent(BookMarkting.this, BookList.class);
					intent4.putExtra("classify", 1+"");
					startActivity(intent4);
					break;
				case R.drawable.kind_other:
					Intent intent5= new Intent(BookMarkting.this,  BookList.class);
					intent5.putExtra("classify", 5+"");
					startActivity(intent5);
					break;
				case R.drawable.kind_policy:
					Intent intent6 = new Intent(BookMarkting.this,BookList.class);
					intent6.putExtra("classify", 2+"");
					startActivity(intent6);
					break;

				}
			}
		});
	}
}