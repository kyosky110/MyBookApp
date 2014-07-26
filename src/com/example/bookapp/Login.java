package com.example.bookapp;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.LoginBean;
import com.example.biz.Const;
import com.example.biz.InternetHelper;
import com.example.biz.MyBookSharePreference;
import com.example.bookapp.R.drawable;
import com.example.util.MD5;
import com.example.util.XmlParseUtil;

public class Login extends Activity implements OnClickListener {
	private EditText mylogin_username, mylogin_password;
	private Button mylogin_btn, mylogin_regist_btn;
	private LoginBean bean;
	private String get_username;
	private String get_userpassword;
	private String sp_username,sp_password;
	private ProgressDialog progressDialog;
	/** �������Ӵ��� */
	private static final int EORROINTERNET = 0;
	/** �˺�������ȷ */
	private static final int SUCCESSINFO = 1;
	/** �˺�������� */
	private static final int EORROINFO = 2;
	// ����Handler����
	private Handler handler = new Handler() {
		@Override
		// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			switch (msg.what) {
			case SUCCESSINFO:
				MyBookSharePreference.save(getApplicationContext(),
						Const.USERNAME, get_username);
				MyBookSharePreference.save(getApplicationContext(),
						Const.PASSWORD, date);
				Intent intent = new Intent(Login.this, MainActivity.class);
				if(get_username !=null){
					intent.putExtra("username", get_username);
				}else{
					intent.putExtra("username", sp_username);
				}
				startActivity(intent);
				finish();
				break;
			case EORROINFO:
				Toast.makeText(getApplicationContext(), "密码或账号错误",
						Toast.LENGTH_SHORT).show();
				break;

			case EORROINTERNET:
				if (progressDialog == null) {
					Toast.makeText(getApplicationContext(), "网络异常",
							Toast.LENGTH_SHORT).show();
					Intent intent1 = new Intent(Login.this, MainActivity.class);
					intent1.putExtra("username", sp_username);
					startActivity(intent1);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "网络异常",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};

	private String date;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp_username = MyBookSharePreference
				.getStr(this, Const.USERNAME);
		sp_password = MyBookSharePreference.getStr(this,
				Const.PASSWORD);
		if (TextUtils.isEmpty(sp_username)
				&& TextUtils.isEmpty(sp_password)) {
			setContentView(R.layout.login);
			mylogin_username = (EditText) findViewById(R.id.login_username);
			mylogin_password = (EditText) findViewById(R.id.login_password);
			mylogin_btn = (Button) findViewById(R.id.login);
			mylogin_regist_btn = (Button) findViewById(R.id.login_regist);
			mylogin_btn.setOnClickListener(this);
			mylogin_regist_btn.setOnClickListener(this);
			Intent intent = getIntent();
			get_username = intent.getStringExtra("username");
			mylogin_username.setText(get_username);
		} else {
			doPost(sp_username,
					sp_password);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.login:
			get_username = mylogin_username.getText().toString().trim();
			get_userpassword = mylogin_password.getText().toString().trim();
			if (TextUtils.isEmpty(get_username)
					|| TextUtils.isEmpty(get_userpassword)) {
				Toast.makeText(getApplicationContext(), "账号或密码不能为空",
						Toast.LENGTH_SHORT).show();
				break;
			}
			date = MD5.getDate(get_userpassword);
			progressDialog = ProgressDialog.show(this, "", "登录中...", true);
			doPost(get_username, date);

			break;

		case R.id.login_regist:
			Intent intent = new Intent(Login.this, Regist.class);
			mylogin_regist_btn.setBackgroundResource(drawable.bt_grey);
			startActivity(intent);
			finish();
			break;
		}
	}

	private void doPost(String get_username, String date) {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("username", get_username);
		map.put("password", date);
		bean = null;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				bean = XmlParseUtil.getLoginInfo(InternetHelper
						.LoginOrRegistPost(Const.LOGIN_URL, map));
				if (bean != null) {
					if (bean.getU_id() == 0) {
						message.what = EORROINFO;
					} else {
						message.what = SUCCESSINFO;
					}
				} else {
					message.what = EORROINTERNET;
				}
				handler.sendMessage(message);
			}
		}).start();

	}
}
