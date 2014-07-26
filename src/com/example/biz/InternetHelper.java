package com.example.biz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class InternetHelper {
	private static HttpClient httpClient;
	public InputStream httpSends(String url,Map<String ,String> rawParams){
		//Ĭ�������
				httpClient = new DefaultHttpClient();
				try{
				//����ʽ
				HttpPost post = new HttpPost(url);
				//����ʵ��
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for(String key:rawParams.keySet()){
					params.add(new BasicNameValuePair(key, rawParams.get(key)));
				}
				UrlEncodedFormEntity entity =  new UrlEncodedFormEntity(params,"utf-8");
				post.setEntity(entity);
				httpClient.getParams().setIntParameter("http.socket.timeout",3000);
				HttpResponse response = httpClient.execute(post);
				if(response.getStatusLine().getStatusCode()==200){
					 String result = EntityUtils  
			                    .toString(response.getEntity()); 
					 InputStream in = new ByteArrayInputStream(result.getBytes()); 
					return in;
					}
				
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					httpClient.getConnectionManager().shutdown();
				}
				return null;
	}
	
	public Bitmap httpImage(String imageUrl,String name){
		Bitmap bitmap = null;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			conn.connect();
			if(conn.getResponseCode()==200){
				is = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len=is.read(buffer))!=-1){
					baos.write(buffer, 0, len);
				}
				baos.close();
				is.close();
				File file = new File(Const.SD_IMAGEDIR+name);
				os = new FileOutputStream(file);
				os.write(baos.toByteArray());
				os.flush();
				bitmap = BitmapFactory.decodeFile(Const.SD_IMAGEDIR+name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			  try {
				if (is != null) {
				      is.close();
				  }
				  if (os != null) {
				      os.close();
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public static InputStream LoginOrRegistPost(String url,Map<String ,String> rawParams) {
		//Ĭ�������
		httpClient = new DefaultHttpClient();
		try{
		//����ʽ
		HttpPost post = new HttpPost(url);
		//����ʵ��
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(String key:rawParams.keySet()){
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		UrlEncodedFormEntity entity =  new UrlEncodedFormEntity(params,"utf-8");
		post.setEntity(entity);
		httpClient.getParams().setIntParameter("http.socket.timeout",3000);
		HttpResponse response = httpClient.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
			 String result = EntityUtils  
	                    .toString(response.getEntity()); 
			 InputStream in = new ByteArrayInputStream(result.getBytes()); 
			return in;
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	public InputStream getServerVersion(String path){
		
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.connect();
			if(conn.getResponseCode()==200){
				return conn.getInputStream();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
