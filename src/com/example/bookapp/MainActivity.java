package com.example.bookapp;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.UpdateBean;
import com.example.biz.Const;
import com.example.biz.DownService;
import com.example.biz.InternetHelper;
import com.example.biz.MyBookSharePreference;
import com.example.biz.getSystemInfo;
import com.example.util.XmlParseUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

//import android.view.Window;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	List<View> list = null;
	Context context;
	@SuppressWarnings("deprecation")
	LocalActivityManager manager = null;
	TabHost host = null;
	ViewPager pager = null;
	final static int MENU_SETUP = Menu.FIRST;
	final static int MENU_RATING = Menu.FIRST + 1;
	final static int MENU_UPLOAD = Menu.FIRST + 2;
	final static int MENU_QUIT = Menu.FIRST + 3;
	
	private final int NAMESAMPLE = 1;
	private final int NAMEDIFFERENT = 2;
	private UpdateBean update;
	
	private Button add_button;
	private String username;

	// ActionBar actionBar;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		add_button = (Button) findViewById(R.id.main_addbook);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		host = (TabHost) findViewById(R.id.tabhost);
		host.setup();
		host.setup(manager);
		context = MainActivity.this;
		pager = (ViewPager) findViewById(R.id.viewpager);
		list = new ArrayList<View>();
		Intent intent1 = new Intent(MainActivity.this, BookMarkting.class);
		intent1.putExtra("username", username);
		Intent intent2 = new Intent(MainActivity.this, MyBookStore.class);
		intent2.putExtra("username", username);
		Intent intent5 = new Intent(MainActivity.this, MySeachView.class);
		intent5.putExtra("username", username);
		list.add(getView("1", intent1));
		list.add(getView("2", intent2));
		list.add(getView("5", intent5));
		/*RelativeLayout indcator1 = (RelativeLayout) LayoutInflater
				.from(context).inflate(R.layout.tabwid, null);
		TextView tabView1 = (TextView) indcator1.findViewById(R.id.textView1);
		tabView1.setText("����");
		RelativeLayout indcator2 = (RelativeLayout) LayoutInflater
				.from(context).inflate(R.layout.tabwid, null);
		TextView tabView2 = (TextView) indcator2.findViewById(R.id.textView1);
		tabView2.setText("����");
		RelativeLayout indcator5 = (RelativeLayout) LayoutInflater
				.from(context).inflate(R.layout.tabwid, null);
		TextView tabView5 = (TextView) indcator5.findViewById(R.id.textView1);
		tabView5.setText("����");*/
		host.addTab(host
				.newTabSpec("A")
				.setIndicator("图书市场")
				.setContent(intent1));
		host.addTab(host.newTabSpec("B").setIndicator("我的书屋").setContent(intent2));
		host.addTab(host.newTabSpec("E").setIndicator("图书查询").setContent(intent5));
		host.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				host.setOnTabChangedListener(new OnTabChangeListener() {

					@Override
					public void onTabChanged(String tabId) {
						// TODO Auto-generated method stub
						if ("A".equals(tabId)) {
							pager.setCurrentItem(0);
						}
						if ("B".equals(tabId)) {
							pager.setCurrentItem(1);
						}
						if ("E".equals(tabId)) {
							pager.setCurrentItem(4);
						}
					}
				});
			}
		});
		pager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				// pager.addView(list.get(position));
				((ViewPager) container).addView(list.get(position));
				return list.get(position);
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				// TODO Auto-generated method stub
				// list.remove(list.get(position));
				((ViewPager) container).removeView(list.get(position));
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				host.setCurrentTab(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	public void onClick(View v){
		Intent intent = new Intent(this,AddBookActivity.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.loginout_title)
					.setMessage(R.string.loginout_msg)
					.setPositiveButton(
							R.string.confirm,
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent startMain = new Intent(
											Intent.ACTION_MAIN);
									startMain.addCategory(Intent.CATEGORY_HOME);
									startMain
											.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(startMain);
									System.exit(0);
								}
							}).setNegativeButton(R.string.cancel, null)
					.create().show();
			return false;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SETUP, 0, "设置");
		menu.add(0, MENU_RATING, 1, "检查更新");
		menu.add(0, MENU_UPLOAD, 2, "切换账号");
		menu.add(0, MENU_QUIT, 3, "退出");
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SETUP:
			Intent intent1 = new Intent(MainActivity.this, SetApp.class);
			startActivity(intent1);
			return true;

		case MENU_UPLOAD:
			MyBookSharePreference.clearDate(this);
			Intent intent2 = new Intent(MainActivity.this, Login.class);
			startActivity(intent2);
			return true;
		case MENU_RATING:
//			Intent intent3 = new Intent(MainActivity.this, MyRatingBar.class);
//			startActivity(intent3);
			getServerInfo();
			return true;
		case MENU_QUIT:
			finish();
			break;

		}
		return false;
	}
	private Handler handle = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case NAMESAMPLE:
				Toast.makeText(context, "现在是最新版本", 1).show();
				break;
				
			case NAMEDIFFERENT:
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("软件升级")
					.setMessage("是否更新到最新版本?")
						.setPositiveButton("更新", 
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(MainActivity.this,DownService.class);
										startService(intent);
										stopService(new Intent(MainActivity.this,DownService.class));
									}
								})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				alert.create().show();
				break;
			}
			
		};
	};
	public void getServerInfo(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				InternetHelper helper = new InternetHelper();
				update = XmlParseUtil.getServerVersionInfo(helper.getServerVersion(Const.SERVERVERSIONURL));
				Message msg = new Message();
//				
				if(update.getVersion().equals(getSystemInfo.getVersion(context))){
					msg.what = NAMESAMPLE;
				}else{
					msg.what = NAMEDIFFERENT;
				}
				handle.sendMessage(msg);
			}
		}).start();
	}
	
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.actionbarmenue, menu);
	 * 
	 * 
	 * return true;
	 * 
	 * }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { case R.id.action_rating:
	 * 
	 * break;
	 * 
	 * default: break; } return super.onOptionsItemSelected(item); }
	 */
}
