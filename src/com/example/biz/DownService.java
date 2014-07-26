package com.example.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bookapp.MainActivity;
import com.example.bookapp.R;

public class DownService extends Service {

	private NotificationManager notificationManager;
	private Notification notification;
	private Intent updateIntent;
	private PendingIntent pendingIntent;
	private int notification_id = 0;
	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;
	/***
	 * 创建通知栏
	 */
	RemoteViews contentView;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_OK:
				// 下载完成，点击安装
				Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),Const.APKNAME));
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(uri,
						"application/vnd.android.package-archive");

				pendingIntent = PendingIntent.getActivity(
						DownService.this, 0, intent, 0);

				notification.setLatestEventInfo(DownService.this,
						"MyBookApp.apk", "下载成功，点击安装", pendingIntent);

				notificationManager.notify(notification_id, notification);

				stopService(updateIntent);
				break;
			case DOWN_ERROR:
				Toast.makeText(getApplicationContext(), "ada", 1).show();
				break;
			default:
				
				break;
			}
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("service is start");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				Uri uri = Uri.parse(Const.DOWNAPK);
				 intent = new  Intent(Intent.ACTION_VIEW, uri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent);
//				createNotification();
//				downApk();
//				Message message = new Message();
//				File file = new File(Environment.getExternalStorageDirectory(),Const.APKNAME);
//				if(file.exists()){
//				 message.what = DOWN_OK;
//				}else{
//					message.what = DOWN_ERROR;
//				}
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	public void createNotification() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		//图标
		notification.icon = R.drawable.ic_launcher;
		contentView = new RemoteViews(getPackageName(),
				R.layout.notification_item);
		contentView.setTextViewText(R.id.notificationTitle, "正在下载");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
		
		notification.contentView = contentView;
		updateIntent = new Intent(this,MainActivity.class);
		updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
		
		notification.contentIntent = pendingIntent;
		
		notificationManager.notify(notification_id, notification);
	}
	public File downApk(){
		File file = new File(Environment.getExternalStorageDirectory(),Const.APKNAME);
		int down_step = 5;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小
		try {
			URL url = new URL(Const.DOWNAPK);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			// 获取下载文件的size
			totalSize = conn.getContentLength();
			if(conn.getResponseCode()==200){
				InputStream is = conn.getInputStream();
				FileOutputStream os = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = is.read(buffer))!=-1){
					os.write(buffer, 0, len);
					downloadCount += len;
					/**
					 * 每次增张5%
					 */
					if (updateCount == 0
							|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
						updateCount += down_step;
						// 改变通知栏
						// notification.setLatestEventInfo(this, "正在下载...", updateCount
						// + "%" + "", pendingIntent);
						contentView.setTextViewText(R.id.notificationPercent,
								updateCount + "%");
						contentView.setProgressBar(R.id.notificationProgress, 100,
								updateCount, false);
						// show_view
						notificationManager.notify(notification_id, notification);

					}
				}
				os.flush();
				os.close();
				is.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
