package com.example.bookapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 
 
 

public class UpLoad extends Activity {

	private Button choosepicbtn;
//	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		/*actionBar = getActionBar();
		// �����Ƿ���ʾӦ�ó���ͼ��
		actionBar.setDisplayShowHomeEnabled(false);
		// ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť
		actionBar.setHomeButtonEnabled(true);
		// ��Ӧ�ó���ͼ������Ϊ�ɵ���İ�ť������ͼ������������ͷ
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();   */     
		choosepicbtn = (Button) findViewById(R.id.choosepicbtn);
		choosepicbtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//
				Intent intent = new Intent(UpLoad.this, CaptureImage.class);
				startActivity(intent);
			}
		});
	}
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	//
	// if (item.isCheckable()) {
	// item.setChecked(true);
	//
	// }
	// if (item.getItemId()==R.drawable.ic_launcher) {
	// Intent intent=new Intent(this,MainActivity.class);
	// startActivity(intent);
	// }
	// return true;
	//
	// }
	
}
