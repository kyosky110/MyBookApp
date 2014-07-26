package com.example.bookapp.borrow;

 
 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bookapp.MyBookStore;
import com.example.bookapp.R;

public class BorrowBook extends Activity implements OnClickListener {
	private Button myborrow_cancell, myborrow_yes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.borrowbook);

		myborrow_cancell = (Button) findViewById(R.id.borrow_cancell);
		myborrow_yes = (Button) findViewById(R.id.borrow_btn);
		myborrow_cancell.setOnClickListener(this);
		myborrow_yes.setOnClickListener(this);
		// myborrow_cancell.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent=new Intent(BorrowBook.this,MyBookStore.class);
		// startActivity(intent);
		// }
		// });
		//

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.borrow_btn:
			myborrow_yes.setText("已成功借的此书，请妥善保管，及时与所有者联系");
			break;

		case R.id.borrow_cancell:
			Intent intent = new Intent(BorrowBook.this, MyBookStore.class);
			startActivity(intent);
			break;
		}
	}
}
