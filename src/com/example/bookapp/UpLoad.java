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
		// 设置是否显示应用程序图标
		actionBar.setDisplayShowHomeEnabled(false);
		// 将应用程序图标设置为可点击的按钮
		actionBar.setHomeButtonEnabled(true);
		// 将应用程序图标设置为可点击的按钮，并在图标上添加向左箭头
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
