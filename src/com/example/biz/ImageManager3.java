package com.example.biz;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.bookapp.R.drawable;

public class ImageManager3 extends AsyncTask<String, Integer, Bitmap> {
	
	private Context context;
	private ImageView imageView;
	private String imageUrl = null;
	private static HashMap<String,SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>() ;
	public ImageManager3(Context context, ImageView imageView) {
		super();
		this.context = context;
		this.imageView = imageView;
	}



	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		imageUrl = params[0];
		//从内存缓存中读取
		if(imageCache.containsKey(params[0])){
			SoftReference<Bitmap> softReference = imageCache.get(params[0]);
			bitmap = softReference.get();
			if(bitmap!= null&&!bitmap.isRecycled())
				return bitmap;
		}
		//从sd卡中读取
		String sdPath = Const.SD_IMAGEDIR+params[1];
		File file = new File(sdPath);
		if(file.exists()){
			bitmap = BitmapFactory.decodeFile(sdPath);
			return bitmap;
		}
		InternetHelper helper = new InternetHelper();
		bitmap = helper.httpImage(params[0], params[1]);
		Log.d("image", "sdcard..." + sdPath);
		Log.d("image2", "sdcard..." + params[0]);
		if(bitmap!=null){
			imageCache.put(params[0], new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result!=null){
			imageView.setImageBitmap(result);
		}else{
			imageView.setImageResource(drawable.d1);
		}
	}
}
