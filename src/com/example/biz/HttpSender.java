package com.example.biz;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
/**����������  ���õ�����������Դ*/
public class HttpSender {
	
	
	/**����*/
	private static HttpSender sender;
	private HttpSender(){
		
	}
	public static HttpSender getInstance(){
		if(sender==null){
			sender = new HttpSender();
		}
		return sender;
	}
	/**添加图书*/
	public void addBook(Map<String,String> map_url,Map<String,String> map_params,HttpTaskListener listener){
		AddBookTask task = new AddBookTask(listener);
		task.execute(map_url,map_params);
	}
	/**按分类查询图书*/
	public void sendQuery(Map<String,String> map_url,Map<String,String> map_params,HttpTaskListener listener){
		QueryBookTask task = new QueryBookTask(listener);
		task.execute(map_url,map_params);
	}
	/**上传图片*/
	public void uploadImage(String path,String name){
		SendImageTask task = new SendImageTask();
		task.execute(path,name);
	}
	
	public void downImage(Context context,ImageView imageView,View view,String imageUrl,String name){
		ImageManager2 task = new ImageManager2(context, imageView,view);
		task.execute(imageUrl,name);
	}
	
	public void detaildownImage(Context context,ImageView imageView,String imageUrl,String name){
		ImageManager3 task = new ImageManager3(context, imageView);
		task.execute(imageUrl,name);
	}
}
