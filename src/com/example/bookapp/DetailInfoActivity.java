package com.example.bookapp;

import com.example.biz.Const;
import com.example.biz.HttpSender;
import com.example.bookapp.R.drawable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lv_item_detail);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String author = intent.getStringExtra("author");
		String content = intent.getStringExtra("content");
		String image = intent.getStringExtra("image");
		String phone = intent.getStringExtra("p_num");
		
		ImageView imageView = (ImageView) findViewById(R.id.detail_image);
		TextView tv_title = (TextView) findViewById(R.id.detail_title);
		TextView tv_author = (TextView) findViewById(R.id.detail_author);
		TextView tv_content = (TextView) findViewById(R.id.detail_content);
		TextView tv_phone = (TextView) findViewById(R.id.detail_phone);
		
		tv_title.setText("标题： "+title);
		tv_author.setText("作者： "+author);
		System.out.println("phone is "+phone);
		if(phone==null||TextUtils.isEmpty(phone)){
			tv_phone.setText("电话： 15045690705");
		}else{
			tv_phone.setText("电话： "+phone);
		}
		tv_content.setText("  简介："+content);
		if(image!= null){
		HttpSender.getInstance().detaildownImage(getApplicationContext(), imageView,  Const.IMAGEPATH_URL+image, image);
		}else{
			imageView.setImageResource(drawable.d1);
		}
	}
	public void onClick(View view){
		if(view.getId()==R.id.booklist_back){
			finish();
		}
	}
}
