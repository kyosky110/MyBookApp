package com.example.bookapp;

import java.util.HashMap;
import java.util.Map;

import com.example.bean.LoginBean;
import com.example.biz.Const;
import com.example.biz.InternetHelper;
import com.example.bookapp.R.drawable;
import com.example.util.MD5;
import com.example.util.XmlParseUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import android.widget.Spinner;
import android.widget.TextView;

public class Regist extends Activity implements OnClickListener {
	private LoginBean bean;
	private Button regist_btn,to_login_btn;
	private EditText register_username, register_password, register_repassword;
	private ProgressDialog progressDialog;
	/** �������Ӵ��� */
	private static final int EORROINTERNET = 0;
	/** ע��ɹ�*/
	private static final int SUCCESSINFO = 1;
	/** �˺��Ѵ��� */
	private static final int EORROINFO = 2;
	
	// ����Handler����
		private Handler handler = new Handler() {
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				progressDialog.dismiss();
				switch (msg.what) {
				case SUCCESSINFO:
					Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Regist.this, Login.class);
					intent.putExtra("username", username);
					startActivity(intent);
					finish();
					break;
				case EORROINFO:
					Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_SHORT).show();
					break;

				case EORROINTERNET:
					Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		private String username;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		register_username	 = (EditText) findViewById(R.id.register_username);
		register_password = (EditText) findViewById(R.id.regist_password);
		register_repassword	 = (EditText) findViewById(R.id.register_repassword);
		regist_btn = (Button) findViewById(R.id.regist_btn);
		to_login_btn = (Button) findViewById(R.id.tologin);
		
	}

	

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regist_btn:
			username = register_username.getText().toString().trim();
			String password = register_password.getText().toString().trim();
			String repassword = register_repassword.getText().toString().trim();
			if (TextUtils.isEmpty(username)
					|| TextUtils.isEmpty(password)||TextUtils.isEmpty(repassword)) {
				Toast.makeText(getApplicationContext(), "账号密码不能为空", Toast.LENGTH_SHORT).show();
				break;
			}
			if(username.length()<6||username.length()>16||
					password.length()<6||password.length()>16){
				Toast.makeText(getApplicationContext(), "账号和密码的长度得在6到16个字符之间", Toast.LENGTH_SHORT).show();
				break;
			}
			if(!password.equals(repassword)){
				Toast.makeText(getApplicationContext(), "密码不相等", Toast.LENGTH_SHORT).show();
				break;
			}
			final Map<String, String> map = new HashMap<String, String>();
			String date = MD5.getDate(password);
			map.put("username", date);
			map.put("password", password);
			bean = null;
			progressDialog = ProgressDialog.show(this, "", "注册中...", true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message message = new Message();
					bean = XmlParseUtil.getLoginInfo(InternetHelper
							.LoginOrRegistPost(Const.REGISTER_URL, map));
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
			break;
			
		case R.id.tologin:
			Intent intent = new Intent(Regist.this, Login.class);
			intent.putExtra("username", "");
			to_login_btn.setBackgroundResource(drawable.bt_grey);
			startActivity(intent);
			finish();
			break;
		}
	}
}
