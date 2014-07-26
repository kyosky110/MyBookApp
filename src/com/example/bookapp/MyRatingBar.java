package com.example.bookapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

//import android.app.ActionBar;
 

public class MyRatingBar extends Activity implements OnRatingBarChangeListener {

	// private ActionBar actionBar;
	private android.widget.RatingBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ratingbar);
		// actionBar = getActionBar();
		// // �����Ƿ���ʾӦ�ó���ͼ��
		// actionBar.setDisplayShowHomeEnabled(false);
		// // ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť
		// actionBar.setHomeButtonEnabled(true);
		// // ��Ӧ�ó���ͼ������Ϊ�ɵ�R�İ�ť������ͼ������������ͷ
		// actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.show();
		bar = (android.widget.RatingBar) findViewById(R.id.userrating);
		bar.setOnRatingBarChangeListener(this);
		bar.setMax(100);
		bar.setProgress(20);

	}

	@Override
	public void onRatingChanged(android.widget.RatingBar ratingBar,
			float rating, boolean fromUser) {
		// TODO Auto-generated method stub
		int currentrating = bar.getProgress();
		Toast.makeText(MyRatingBar.this,
				"currentrating" + currentrating + "ratingBar" + ratingBar,
				Toast.LENGTH_SHORT).show();
	}
}